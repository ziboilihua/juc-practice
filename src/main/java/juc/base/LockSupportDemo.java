package juc.base;

import java.util.concurrent.locks.LockSupport;

public class LockSupportDemo {

    static Object o = new Object();

    static class LockSupportThread extends Thread {

        private String name;

        public LockSupportThread(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println(name + "开始执行");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name + " park");
            LockSupport.park();
            if (Thread.currentThread().isInterrupted()) {
                System.out.println(name +"已经被中断");
            }
            System.out.println(name + "结束");
        }
    }

    public static void main(String[] args) {
        Thread t1 = new LockSupportThread("t1");
        t1.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t1.interrupt();
        //LockSupport.unpark(t1);
    }
}
