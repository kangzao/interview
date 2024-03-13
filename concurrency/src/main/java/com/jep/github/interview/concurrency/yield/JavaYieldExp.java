package com.jep.github.interview.concurrency.yield;

public class JavaYieldExp extends Thread {
    public void run() {
        for (int i = 0; i < 3; i++)
            System.out.println(Thread.currentThread().getName() + " in control----run");
    }

    public static void main(String[] args) {
        JavaYieldExp t1 = new JavaYieldExp();
        JavaYieldExp t2 = new JavaYieldExp();
        // this will call run() method  
        t1.start();
        t2.start();
        for (int i = 0; i < 3; i++) {
            // Control passes to child thread  
            Thread.yield();
            System.out.println(Thread.currentThread().getName() + " in control");
        }
    }
}  