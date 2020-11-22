package juc.base;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Reentranlock 和Synchronized区别 sync 系统自带,系统自动加锁自动释放(抛出异常会自动释放)
 * reen 需要手动加锁手动释放(抛出异常不会自动释放)使用时需要try finally unlock,
 * 使用tryLock 定时获取锁获取不到就算了
 * 使用lockInterruptibly 锁可以被中断并抛出InterruptedException
 * 可以公平和非公平切换 sync只支持公平
 * 都是可重入锁
 * reen 使用CAS来实现
 * sync 锁升级 无锁 - 偏向锁 - 轻量级锁(内旋锁CAS) - 重量级锁 (进入等待队列)
 */
public class ReentranlockDemo {

    private ReentrantLock lock = new ReentrantLock();

    void m1() {
        try {
            lock.lock();
            for (int i = 0; i < 10; i++) {
                System.out.println(i);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            lock.unlock();
        }

    }

    void m2() {
        try {
            lock.lock();
            System.out.println("finished");
        } finally {
            lock.unlock();
        }
    }

    void m3() {
        try {
            System.out.println("m3 开始执行");
            lock.lock();
            TimeUnit.SECONDS.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    void m4() {
        try {
            System.out.println("m4 开始执行");
            lock.lockInterruptibly();
            TimeUnit.SECONDS.sleep(5L);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("m4 被打断");
        }
    }

    public static void main(String[] args) {
        ReentranlockDemo r = new ReentranlockDemo();
        /*new Thread(() -> {
            r.m1();
        }).start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            r.m2();
        }).start();*/

        Thread t1 = new Thread(() -> {

            r.m3();
            System.out.println("m3 结束执行");
        });
        t1.start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread t2 = new Thread(() -> {
            r.m4();
        });
        t2.start();
        t2.interrupt();


    }
}
