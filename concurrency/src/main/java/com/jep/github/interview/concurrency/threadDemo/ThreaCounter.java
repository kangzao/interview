package com.jep.github.interview.concurrency.threadDemo;

public class ThreaCounter extends Thread {
    private static int cnt = 0;

    @Override
    public void run() {
        for (int i = 0; i < 100000; i++) {
            cnt++;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreaCounter t1 = new ThreaCounter();
        ThreaCounter t2 = new ThreaCounter();
        ThreaCounter t3 = new ThreaCounter();
        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();
        System.out.println("cnt is " + cnt);
    }
}