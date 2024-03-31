package com.jep.github.interview.concurrency.threadDemo;

import java.io.IOException;

public class ThreadState {
    public static void main(String[] args) {
        Object lock = new Object();
        Thread a = new Thread(() -> {
            synchronized (lock) {
                try {
                    System.in.read();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        a.setName("Thread A");
        Thread b = new Thread(() -> {
            synchronized (lock) {
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        b.setName("Thread B");
        a.start();
        b.start();
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}


