package com.fenxiangz.learn.juc;

public class TestVolatile {
    public static void main(String[] args) { //这个线程是用来读取flag的值的
        ThreadDemo threadDemo = new ThreadDemo();
        Thread thread = new Thread(threadDemo);
        thread.start();
        while (true) {
            //volatile 保证线程对 flag filed 的可见性
            if (threadDemo.isFlag()) {
                System.out.println("主线程读取到的flag = " + threadDemo.isFlag());
                break;
            }
        }
    }
}

/**
 * 这个线程是用来修改flag的值的
 */
class ThreadDemo implements Runnable {
    /**
     * volatile 必须定义 ，否则主线程无法退出循环
     */
    public volatile boolean flag = false;

    @Override
    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        flag = true;
        System.out.println("ThreadDemo线程修改后的flag = " + isFlag());
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
