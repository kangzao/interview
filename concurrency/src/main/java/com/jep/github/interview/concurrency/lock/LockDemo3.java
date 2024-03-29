package com.jep.github.interview.concurrency.lock;

import java.util.concurrent.locks.Lock;

public class LockDemo3 {
    int i = 0;

    //    Lock lock = new MyLock();
    Lock lock = new MyLockUseAqs();


    public void add() { // 参考我的源码注释。
        lock.lock();
        try {
            i++;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        LockDemo3 ld = new LockDemo3();

        for (int i = 0; i < 4; i++) {
            new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    ld.add();
                }
            }).start();
        }
        Thread.sleep(2000L);
        System.out.println(ld.i);
    }
}