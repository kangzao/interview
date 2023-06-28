package com.jep.github.interview.concurrency.threadDemo;

public class InterruptExample {
    public static void main(String[] args) {
        MyTestThread myThread = new MyTestThread();
        myThread.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        myThread.interrupt();
    }
}

class MyTestThread extends Thread {
    @Override
    public void run() {
        while (!isInterrupted()) {
            System.out.println("Thread is running...");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted!");
                // 重新设置中断状态，以便在循环条件中捕获
                interrupt();

            }
        }
    }
}