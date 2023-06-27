package com.jep.github.interview.concurrency.atomic;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class AtomicIntegerArrayExample {
    private static AtomicIntegerArray atomicArray = new AtomicIntegerArray(new int[]{0, 0, 0});

    public static void main(String[] args) throws InterruptedException {
// 创建两个线程，分别对数组进行自增操作
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < atomicArray.length(); i++) {
                int value = atomicArray.getAndIncrement(i);
                System.out.println("Thread 1: Incremented value at index " + i + ": " + value);
            }
        });
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < atomicArray.length(); i++) {
                int value = atomicArray.getAndIncrement(i);
                System.out.println("Thread 2: Incremented value at index " + i + ": " + value);
            }
        });
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
// 输出最终的数组值
        System.out.println("Final array: " + atomicArray);
    }
}