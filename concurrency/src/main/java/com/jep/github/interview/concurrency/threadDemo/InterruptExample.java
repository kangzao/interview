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
        while (!isInterrupted()) { //如果被中断，返回false
            System.out.println("Thread is running...");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {//抛异常之前清空中断标志位
                System.out.println("Thread interrupted!");
                // 重新设置中断状态，以便在循环条件中捕获 false
                interrupt();//true
            }
        }
    }
}