package com.jep.github.interview.concurrency.threadDemo;

public class ThreadPriority extends Thread {
    public ThreadPriority(String name) {
        super(name);
    }

    @Override
    public void run() {
        for (int i = 0; i < 50; i++) {
            System.out.println(getName() + ",优先级是：" + getPriority() + ",i=" + i);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPriority low = new ThreadPriority("low");
        low.setPriority(Thread.MIN_PRIORITY);
        ThreadPriority high = new ThreadPriority("high");
        high.setPriority(Thread.MAX_PRIORITY);
        low.start();
        high.start();
        low.join();
        high.join();
    }
}