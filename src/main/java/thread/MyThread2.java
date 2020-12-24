package thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MyThread2 {

    private static  int a = 0;
    public static void main(String[] args) {

        // 1.创建一个固定线程池
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1000);
        // 2.创建一个计数器
        CountDownLatch countDownLatch = new CountDownLatch(1000);
        // 2.创建5个线程执行
        for (int i = 0; i < 1000; i++) {
            Runnable run = new Runnable() {
                @Override
                public synchronized void run() {
                    a= a+1;
                    System.out.println("我是线程"+Thread.currentThread().getName()+"------"+a);
                    //线程执行结束计数器减1
                    countDownLatch.countDown();
//                    try {
//                        Thread.sleep(10000); //睡眠0.1秒
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                }
            };
            fixedThreadPool.execute(run);
        }

        try {
            countDownLatch.await(1, TimeUnit.MINUTES);  //阻塞当前线程直到计数器归0
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        fixedThreadPool.shutdown(); //结束线程
        System.out.println("最后才执行主线程-------"+a);
    }

}
