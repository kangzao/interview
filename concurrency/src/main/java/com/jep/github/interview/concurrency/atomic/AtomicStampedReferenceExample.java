package com.jep.github.interview.concurrency.atomic;

import java.util.concurrent.atomic.AtomicStampedReference;

public class AtomicStampedReferenceExample {
    private static AtomicStampedReference<Integer> atomicStampedRef = new AtomicStampedReference<>(0, 0);

    public static void main(String[] args) throws InterruptedException {
// 创建两个线程，分别尝试修改变量的值
        Thread thread1 = new Thread(() -> {
// 获取当前版本
            int stamp = atomicStampedRef.getStamp();
// 尝试将变量的值从0修改为1，版本号加1
            boolean success = atomicStampedRef.compareAndSet(0, 1, stamp, stamp + 1);
            System.out.println("Thread 1: Value modified: " + success);
        });

        Thread thread2 = new Thread(() -> {
            int stamp = atomicStampedRef.getStamp();
// 先将变量的值从0修改为1，再修改回0   1 1
            atomicStampedRef.compareAndSet(0, 1, stamp, stamp + 1);
            atomicStampedRef.compareAndSet(1, 0, stamp + 1, stamp + 2);
            System.out.println("Thread 2: Value modified back to 0");
        });
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
// 输出最终的变量值和标记值
        System.out.println("Final value: " + atomicStampedRef.getReference());
        System.out.println("Final stamp: " + atomicStampedRef.getStamp());
    }
}