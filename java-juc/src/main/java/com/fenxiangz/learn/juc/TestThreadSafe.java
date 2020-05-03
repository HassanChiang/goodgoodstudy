package com.fenxiangz.learn.juc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class TestThreadSafe implements Runnable {
    private static int i = 0;
    private static AtomicInteger i2 = new AtomicInteger();

    public static void insert() {
        for (int j = 0; j < 1000; j++) {
            i++;
            i2.incrementAndGet();
        }
    }


    /**
     * 多执行几次，查看效果
     * @param args
     */
    public static void main(String[] args) {
        ExecutorService executors = Executors.newFixedThreadPool(16);
        for (int j = 0; j < 16; j++) {
            executors.execute(new TestThreadSafe());
        }
        executors.shutdown();
        while (true) {
            if (executors.isTerminated()) {
                //线程不安全的i++，每次结果都可能不一样
                System.out.println(i);
                //线程安全的原子操作，每次结果都是 16000
                System.out.println(i2.get());
                break;
            }
        }
    }

    @Override
    public void run() {
        insert();
    }
}