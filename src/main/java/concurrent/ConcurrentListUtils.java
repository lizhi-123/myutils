package concurrent;

import shijiyingtong.ThreadRead;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**并发处理一个集合*/
public class ConcurrentListUtils {

    public static void main(String[] args) throws InterruptedException {
        List<Integer> list  = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            list.add(i);
        }

        ExecutorService exec = Executors.newFixedThreadPool(5);
        List<Callable<Integer>> threadList  = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            threadList.add(() -> {
                System.out.println(Thread.currentThread().getName() + "-执行-");
                return 1;
            }
            );
        }
        exec.invokeAll(threadList);
        exec.shutdown();
        System.out.println("等待线程结束？");

    }
}


