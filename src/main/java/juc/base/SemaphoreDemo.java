package juc.base;

import java.util.concurrent.Semaphore;

public class SemaphoreDemo {

    public static void main(String[] args) {
        // 限流控制并发量, 是否公平
        Semaphore s = new Semaphore(1, false);

        new Thread(() -> {
            try {
                s.acquire();
                System.out.println(Thread.currentThread().getName() + "start");
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + "end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                s.release();
            }
        }).start();


        new Thread(() -> {
            try {
                s.acquire();
                System.out.println("t1: start");
                Thread.sleep(1000);
                System.out.println("t1: end");
                s.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                s.acquire();
                System.out.println("t2: start");
                Thread.sleep(1000);
                System.out.println("t2: end");
                s.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
