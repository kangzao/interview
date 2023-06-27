package com.jep.github.interview.concurrency.atomic;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceExample {
    public static void main(String[] args) {
        AtomicReference<String> atomicReference = new AtomicReference<>("initial value");

        // 线程1修改引用类型变量的值
        Thread thread1 = new Thread(() -> {
            String oldValue = atomicReference.get();
            String newValue = "new value";
            boolean success = atomicReference.compareAndSet(oldValue, newValue);//1(version:0) 2      1（version:0） - 2(version:1) - 1(version:2) ABA
            System.out.println("Thread 1: success = " + success + ", oldValue = " + oldValue + ", newValue = " + newValue);
        });

        // 线程2修改引用类型变量的值
        Thread thread2 = new Thread(() -> {
            String oldValue = atomicReference.get();
            String newValue = "another new value";
            boolean success = atomicReference.compareAndSet(oldValue, newValue);
            System.out.println("Thread 2: success = " + success + ", oldValue = " + oldValue + ", newValue = " + newValue);
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Final value: " + atomicReference.get());
    }
}
