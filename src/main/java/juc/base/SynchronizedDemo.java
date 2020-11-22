package juc.base;

import java.util.concurrent.TimeUnit;

public class SynchronizedDemo {

    /**
     * synchronized
     * 修饰静态方法是针对SynchronizedDemo.class加锁
     * 修饰成员方法是针对this 加锁
     * 修饰对象时针对某个对象加锁
     *
     * 锁升级
     * 锁的状态: 无锁、偏向锁、自旋锁(CAS)、重量级锁
     * 1.偏向锁:当一个线程调用同步方法时会针对锁的对象头添加threadId,当同一个线程访问时只需判断该对象的threadId与当前线程是否相同
     * 偏向锁默认开启,但是开启时间比程序启动慢几秒可通过XX:BiasedLockingStartUpDelay = 0进行设置
     * 不想使用偏向锁可通过-XX:-UseBiasedLocking = false关闭
     * 2.自旋锁: while(i<10) i++ 重复10次,当重复10次无法拿到锁时,升级为重量级锁(占CPU资源)
     * 3.重量级锁 进入系统内核等待队列(不占用CPU资源)
     */

    private int count = 0;

    public synchronized void add() {
        for (int i = 0; i < 10; i++) {
            System.out.println("count:" + ++count);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setCount(Integer set) {
        synchronized (this) {
            count = set;
        }
    }

    public synchronized void m1() {
        System.out.println("m1 start");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        m2();
        System.out.println("m1 end");
    }

    public synchronized void m2() {
        System.out.println("m2 start");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("m2 end");
    }

    public void printCount() {
        System.out.println("printCount:" + count);
    }

    public static void main(String[] args) {
        SynchronizedDemo synchronizedDemo = new SynchronizedDemo();
        /*new Thread(() -> {
            synchronizedDemo.add();
        }).start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            synchronizedDemo.setCount(100);
            synchronizedDemo.printCount();
        }).start();*/

        new Thread(synchronizedDemo::m1).start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(synchronizedDemo::m2).start();


    }

}
