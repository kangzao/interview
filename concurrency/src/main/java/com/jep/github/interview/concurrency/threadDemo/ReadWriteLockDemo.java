package com.jep.github.interview.concurrency.threadDemo;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockDemo {
    private static final ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock(
            false);
    // 读锁
    private static final ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock
            .readLock();
    // 写锁
    private static final ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock
            .writeLock();

    private static void read() {
        // 获取读锁，读锁之间不会互斥
        readLock.lock();
        try {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + "得到读锁，正在读取");
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + "释放读锁");
            readLock.unlock();
        }
    }

    private static void write() {
        // 获取写锁，写锁会排斥写锁和读锁
        writeLock.lock();
        try {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + "得到写锁，正在写入");
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + "释放写锁");
            writeLock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> write()).start();
        new Thread(() -> write()).start();
        new Thread(() -> read()).start();
        new Thread(() -> read()).start();
    }
}