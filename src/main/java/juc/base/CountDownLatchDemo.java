package juc.base;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CountDownLatchDemo {
    // 乐观锁 CAS、悲观锁 synchronized、自旋锁CAS、读写锁(共享锁，排他锁) Reentrant read write Lock 分段锁 LongAdder
    static CountDownLatch countDownLatch = new CountDownLatch(5);

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            new Thread(() -> {
                System.out.println(finalI + "start");
                try {
                    Thread.sleep((finalI+1) * 1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(finalI + "end");
                countDownLatch.countDown();

            }).start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("end");
    }
}
