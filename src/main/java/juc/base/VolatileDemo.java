package juc.base;

import java.util.concurrent.TimeUnit;

public class VolatileDemo {
    /**
     * volatile
     * 1.保证该变量其他线程可见
     * 2.禁止指令重排序
     */

    static volatile boolean hasNew = false;

    public static void main(String[] args) {
        new Thread(() -> {
            while (!hasNew) {
            }
            System.out.println("已改变");
        }).start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            hasNew = true;
            System.out.println("改变完成");
        }).start();
    }
}
