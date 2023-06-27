package com.jep.github.interview.concurrency.atomic;

import java.util.concurrent.atomic.AtomicMarkableReference;

public class AtomicMarkableReferenceExample {
    public static void main(String[] args) {
        AtomicMarkableReference<String> atomicReference = new AtomicMarkableReference<>("initial value", false);
// 线程1修改引用类型变量的值
        Thread thread1 = new Thread(() -> {
            String oldValue = atomicReference.getReference();
            String newValue = "new value";
            boolean success = atomicReference.compareAndSet(oldValue, newValue, false, true);
            System.out.println("Thread 1: success = " + success + ", oldValue = " + oldValue + ", newValue = " + newValue);
        });
// 线程2修改引用类型变量的值
        Thread thread2 = new Thread(() -> {
            String oldValue = atomicReference.getReference();
            String newValue = "another new value";
            boolean success = atomicReference.compareAndSet(oldValue, newValue, false, true);
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
        System.out.println("Final value: " + atomicReference.getReference());
    }
}