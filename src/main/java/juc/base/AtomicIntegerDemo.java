package juc.base;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

public class AtomicIntegerDemo {

    private Long count = 0L;

    // 保证原子操作使用CAS 不加锁 (乐观锁) 使用unSafe类直接操作内存(11以后无法使用)
    /**
     * cas
     * ①int expected = read m
     * ②cas (expected, 1)
     * 如果内存里面m = expected 那就+1 ,不然就重复①
     */
    private AtomicLong atomicLong = new AtomicLong(0L);

    // 高并发场景更快(分段锁)
    private LongAdder longAdder = new LongAdder();

    void add(){
        count++;
        atomicLong.incrementAndGet();
        longAdder.add(1L);
    }

    void print() {
        System.out.println("count:" + count);
        System.out.println("atomicLong:" + atomicLong.get());
        System.out.println("longAdder:" + longAdder.longValue());
    }

    public static void main(String[] args) {
        AtomicIntegerDemo a = new AtomicIntegerDemo();
        int threadCount = 100;
        Thread[] threads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    a.add();
                }
            });
            threads[i].start();
        }

        for (int i = 0; i < threadCount; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        a.print();

    }

}
