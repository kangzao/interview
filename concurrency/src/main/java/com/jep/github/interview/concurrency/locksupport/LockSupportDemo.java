package com.jep.github.interview.concurrency.locksupport;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class LockSupportDemo {
    // 先 unpark 后 park 也是可以运行成功的,不像 wait 和 notify 会直接报错
    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            // 暂停几秒钟
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "进入");
            LockSupport.park(); // 如果先 unpark 后 park , 那么这行会直接不起作用
            System.out.println(Thread.currentThread().getName() + "被唤醒");
        }, "线程1");
        thread1.start();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "通知");
            LockSupport.unpark(thread1);
        }, "线程2").start();
    }
}
/**
 * 线程2通知线程1进入线程1被唤醒
 * <p>
 * 线程1进入
 * 线程2通知
 * 线程1被唤醒
 * <p>
 * 线程1进入
 * 线程2通知
 * 线程1被唤醒
 * <p>
 * 线程1进入
 * 线程2通知
 * 线程1被唤醒
 * <p>
 * 线程1进入
 * 线程2通知
 * 线程1被唤醒
 * <p>
 * 线程1进入
 * 线程2通知
 * 线程1被唤醒
 **/
/**
 * 线程1进入
 * 线程2通知
 * 线程1被唤醒
 **/