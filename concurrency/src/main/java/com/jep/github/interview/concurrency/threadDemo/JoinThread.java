package com.jep.github.interview.concurrency.threadDemo;

public class JoinThread extends Thread {
    public JoinThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            System.out.println(getName() + " " + i);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        JoinThread jt = new JoinThread("被Join的线程");
        jt.setDaemon(true);
        jt.start();
        jt.join();
        System.out.println("Main wait util JoinThread Finish");

    }
}