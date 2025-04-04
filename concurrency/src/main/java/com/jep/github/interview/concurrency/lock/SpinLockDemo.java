package com.jep.github.interview.concurrency.lock;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class SpinLockDemo {
    AtomicReference atomicReference = new AtomicReference(); //临界资源

    public void lock() throws InterruptedException {
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName() + "\t 加锁");
        //设置atomicReference对象的成员变量V为 thread，如果设置成功，则退出 while，如果设置失败，自旋
        System.out.println("atomicReference ---" + atomicReference);
        while (!atomicReference.compareAndSet(null, thread)) {
            TimeUnit.MILLISECONDS.sleep(200);
            System.out.println("execute lock method ===" + thread.getName());
        }
    }

    public void unLock() {
        Thread thread = Thread.currentThread();
        //将atomicReference中的V变量 改成null
        atomicReference.compareAndSet(thread, null);

        System.out.println(Thread.currentThread().getName() + "\t UnLock over");
    }

    public static void main(String[] args) {
        SpinLockDemo spinLockDemo = new SpinLockDemo();
        new Thread(() -> {
            try {
                spinLockDemo.lock();
                System.out.println(Thread.currentThread().getName() + "---->" + new Date());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName() + "线程结束睡眠");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            spinLockDemo.unLock();
        }, "A").start(); //暂停一会儿线程，保证A线程先于B线程启动并完成


        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "---->" + new Date());
            try {
                spinLockDemo.lock();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(new Date());
            spinLockDemo.unLock();
        }, "B").start();
    }
}