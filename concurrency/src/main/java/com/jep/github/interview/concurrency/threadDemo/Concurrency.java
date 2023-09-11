package com.jep.github.interview.concurrency.threadDemo;

import java.util.concurrent.TimeUnit;

public class Concurrency {
    public static void main(String[] args) {
        Thread thread = new Thread(new DaemonRunner(), "DaemonRunner");
        thread.setDaemon(true);
        thread.start();//主线程已经结束
    }

    static class DaemonRunner implements Runnable {
        @Override
        public void run() {
            try {
                try {
                    TimeUnit.SECONDS.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } finally {
                System.out.println("DaemonThread finally run."); // finally块不会执行
            }
        }
    }
}