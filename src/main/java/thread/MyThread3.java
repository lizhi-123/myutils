package thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyThread3 {
    static int a =0;
    public static void main(String[] args) {
        ExecutorService service =  Executors.newFixedThreadPool(1000);
        for (int i = 0; i < 1000; i++) {
            service.execute(new Runnable() {
                @Override
                public void run() {
                    a=a++;
                    System.out.println("线程："+Thread.currentThread().getName()+"----"+a);
                }
            });
        }
        System.out.println("a的值：================================================================>"+a);
    }
}
