package juc.base;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockDemo {

    static class ReadWriteObj {
        static ReadWriteLock readWriteLock;
        static Lock lock;
        static Lock readLock;
        static Lock writeLock;
        static String str = "";
        static {
            readWriteLock = new ReentrantReadWriteLock();
            lock = new ReentrantLock();
            readLock = readWriteLock.readLock();
            writeLock = readWriteLock.writeLock();
        }

        public static void readStr() {
            try{
                readLock.lock();
                System.out.println(Thread.currentThread().getName() + "开始读数据");
                Thread.sleep(500);
                System.out.println(Thread.currentThread().getName() + ":" + str);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                readLock.unlock();
            }
        }

        public static void writeStr(String data) {
            try{
                writeLock.lock();
                System.out.println(Thread.currentThread().getName() + "开始写数据");
                Thread.sleep(5000);
                str = data;
                System.out.println(Thread.currentThread().getName() + "写数据完成");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                writeLock.unlock();
            }
        }


    }

    public static void main(String[] args) {
        // 10个线程读数据
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                ReadWriteObj.readStr();
            }).start();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            ReadWriteObj.writeStr("10");
        }).start();


    }
}
