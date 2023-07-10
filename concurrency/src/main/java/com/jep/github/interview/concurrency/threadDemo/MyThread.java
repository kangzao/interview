package com.jep.github.interview.concurrency.threadDemo;

/*
 * @author: enping.jep
 * @create: 2021-02-02 3:33 下午
 * 继承Thread类启动线程
 */
public class MyThread extends Thread {

    public void run() {
        System.out.println("MyThread run");
    }

    public static void main(String args[]) {
        MyThread thread1 = new MyThread();
        MyThread thread2 = new MyThread();
        thread1.start();
        thread2.start();

    }

}
