package com.jep.github.interview.concurrency.yield;

import java.util.concurrent.locks.ReentrantLock;

public class LockExample {
    // 创建锁对象
    private static final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        try {
            // 此处异常
            int num = 1 / 0;
            // 加锁操作
            lock.lock();
        } finally {
            // 释放锁
            lock.unlock();
            System.out.println("锁释锁");
        }
        System.out.println("程序执行完成.");
    }
}