package juc.base;

import java.util.concurrent.TimeUnit;

public class VolatileDemo {
    /**
     * volatile
     * 1.可见性 缓存一致性协议
     * 2.禁止指令重排序 JMM (java memory model) 8个指令完成数据读写,
     * 通过load 和 store 互相组合成4个内存屏障禁止指令重排序
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
