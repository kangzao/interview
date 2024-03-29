package com.jep.github.interview.concurrency.threadDemo;

public class SynchronizedDemo {

    static Integer count = 0;


    static void incr() {
        synchronized (count) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count++;
        }
    }


    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    SynchronizedDemo.incr();
                }
            }).start();
            Thread.sleep(200);
            System.out.println("result:" + count);
        }
    }
}