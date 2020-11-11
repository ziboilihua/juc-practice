package juc.base;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclibarriarDemo {

    static CyclicBarrier cyclicBarrier = new CyclicBarrier(10, () -> {
        System.out.println("执行了10次了");
    });

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            int finalI = i;
            new Thread(() -> {
                System.out.println("线程" + finalI + "开始执行");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }

    }
}
