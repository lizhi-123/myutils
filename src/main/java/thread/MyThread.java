package thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyThread {
    /**多个线程执行结束再执行主线程*/
    public static void main(String[] args) {
        // 1.创建一个固定线程池
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
        // 2.创建5个线程执行
        for (int i = 0; i < 5; i++) {
            Runnable runnable = () -> {
//                try {
//                    Thread.sleep(10000); //睡眠0.1秒
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                System.out.println("我是线程"+Thread.currentThread().getName());
            };
            fixedThreadPool.submit(runnable);
        }
        fixedThreadPool.shutdown();
        while (true) {//等待所有任务都执行结束
            if (fixedThreadPool.isTerminated()) {//所有的子线程都结束了
                // 3.测试能否执行完成后再执行主线程
                System.out.println("最后才执行主线程-------");
                break;
            }
        }

    }
}
