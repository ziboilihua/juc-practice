package juc.base;

import java.util.concurrent.Exchanger;

public class ExchangerDemo {

    static Exchanger<String> exchanger = new Exchanger();

    public static void main(String[] args) {
        // 线程间交换数据
        new Thread(() -> {
            String s = "t1";
            try {
                Thread.sleep(2000);
                exchanger.exchange(s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("thread1:" + s);
        }).start();

        new Thread(() -> {
            String s = "t2";
            try {
                exchanger.exchange(s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("thread2:" + s);
        }).start();
    }
}
