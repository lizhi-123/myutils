package shijiyingtong;

import cn.hutool.core.text.csv.CsvRow;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.util.SystemOutLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ThreadRead {


    public static void readList(List<CsvRow> tList) throws Exception {

        // 开始时间
        long start = System.currentTimeMillis();
        List<CsvRow> list = tList;

//        for (int i = 1; i <= 329000; i++) {
//            list.add(i + "");
//        }
        // 每3万条数据开启一条线程
        int threadSize = 40000;
        // 总数据条数
        int dataSize = list.size();
        // 线程数
        int threadNum = dataSize / threadSize + 1;
        // 定义标记,过滤threadNum为整数
        boolean special = dataSize % threadSize == 0;

        // 创建一个线程池
        ExecutorService exec = Executors.newFixedThreadPool(threadNum);
        // 定义一个任务集合
        List<Callable<Integer>> tasks = new ArrayList<>();
        Callable<Integer> task = null;
        List<CsvRow> cutList = null;

        // 确定每条线程的数据
        for (int i = 0; i < threadNum; i++) {
            if (i == threadNum - 1) {
                if (special) {
                    break;
                }
                cutList = list.subList(threadSize * i, dataSize);
            } else {
                cutList = list.subList(threadSize * i, threadSize * (i + 1));
            }
            // System.out.println("第" + (i + 1) + "组：" + cutList.toString());
            final List<CsvRow> listStr = cutList;
            task = new Callable<Integer>() {

                @Override
                public Integer call() throws Exception {
                    System.out.println(Thread.currentThread().getName() + "线程开始：" + listStr.get(0).getRawList().toString());
                    System.out.println(Thread.currentThread().getName() + "线程结束：" + listStr.get(listStr.size()-1).getRawList().toString());
                    //数据处理
                    ForApi.querySave(listStr);
                    return 1;
                }
            };
            // 这里提交的任务容器列表和返回的Future列表存在顺序对应的关系
            tasks.add(task);
        }

        List<Future<Integer>> results = exec.invokeAll(tasks);

        for (Future<Integer> future : results) {
            System.out.println(future.get());
        }

        // 关闭线程池
        exec.shutdown();
        System.out.println("线程任务执行结束");
        System.err.println("执行任务消耗了 ：" + (System.currentTimeMillis() - start) + "毫秒");
    }
}
