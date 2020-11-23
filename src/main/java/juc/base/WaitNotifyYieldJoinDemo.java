package juc.base;

import com.sun.org.apache.xpath.internal.WhitespaceStrippingElementMatcher;

public class WaitNotifyYieldJoinDemo {

    public static Object o = new Object();

    // wait sleep 如果在同步方法内都不会释放持有的锁 wait 可以等待几秒后自动继续执行或者被调用持有对象的锁的notify方法可以唤醒
    public void m1() {
        for (int i = 0; i < 10; i++) {
            System.out.println("m1: " + i);
            if (i == 4) {
                // 会释放CPU资源,但不会释放锁所以在同步代码中调用无用
                Thread.yield();
            }
        }
    }


    public void m2() {
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("m2: " + i);
        }
    }

    public synchronized void m3() {
        for (int i = 0; i < 10; i++) {
            System.out.println("m3: " + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (i == 5) {
                o.notify();
            }

        }
    }

    public void m4() {
        synchronized (o) {
            for (int i = 0; i < 10; i++) {
                System.out.println("m4: " + i);
                if (i == 5) {
                    try {
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }

    static class InterruptedDemo extends Thread {
        @Override
        public void run() {
            System.out.println("start");
            while (!Thread.currentThread().isInterrupted()) {

            }
            System.out.println("interrupted");
        }
    }

    public static void main(String[] args) {
        /*WaitNotifyYieldJoinDemo w = new WaitNotifyYieldJoinDemo();
        *//*new Thread(w::m1).start();
        new Thread(w::m2).start();*//*
        // new Thread(w::m2).start();
        *//**//*
        Thread t4 = new Thread(w::m4);
        t4.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t4.interrupt();*/
        //t4.interrupt();
        //new Thread(w::m4).start();
        InterruptedDemo i = new InterruptedDemo();
        i.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // interrupt 不会释放锁只会修改中断标记
        i.interrupt();

    }
}
