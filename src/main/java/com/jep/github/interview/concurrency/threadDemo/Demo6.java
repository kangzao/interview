package com.jep.github.interview.concurrency.threadDemo;

import java.util.concurrent.locks.LockSupport;

/**
 * @author enping
 * @date 2021/4/17 下午12:41
 * wait notify park unpark的用法
 **/
public class Demo6 {
    /**
     * 资源
     */
    public static Object resource = null;

    /** 正常的wait/notify */
    public void waitNotifyTest() throws Exception {
        new Thread(() -> {
            synchronized (this) {
                while (resource == null) {
                    System.out.println("1、进入等待");
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("2、拿到资源");
        }).start();
        //3秒之后生产资源
        Thread.sleep(3000L);
        resource = new Object();
        synchronized (this) {
            this.notifyAll();
            System.out.println("3、通知消费者");
        }

    }


    /**
     * 会导致程序永久等待的wait/notify
     */
    public void waitNotifyDeadLockTest() throws Exception {
        // 启动线程
        new Thread(() -> {
            if (resource == null) { // 如果没资源，则进入等待
                try {
                    Thread.sleep(5000L);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                synchronized (this) {
                    try {
                        System.out.println("1、进入等待");
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("2、买到包子，回家");
        }).start();
        // 3秒之后，生产资源
        Thread.sleep(3000L);
        resource = new Object();
        synchronized (this) {
            this.notifyAll();
            System.out.println("3、通知消费者");
        }
    }

    /** 正常的park/unpark */
    public void parkUnparkTest() throws Exception {
        Thread consumerThread = new Thread(() -> {
            while (resource == null) {
                System.out.println("1、进入等待");
                LockSupport.park();
            }
            System.out.println("2、获取到资源");
        });
        consumerThread.start();
        Thread.sleep(3000);
        resource = new Object();
        LockSupport.unpark(consumerThread);
        System.out.println("3、通知消费者");

    }


    /**
     * 死锁的park/unpark
     */
    public void parkUnparkDeadLockTest() throws Exception {
        // 启动线程
        Thread consumerThread = new Thread(() -> {
            if (resource == null) { // 如果没包子，则进入等待
                System.out.println("1、进入等待");
                // 当前线程拿到锁，然后挂起
                synchronized (this) {
                    LockSupport.park();
                }
            }
            System.out.println("2、买到包子，回家");
        });
        consumerThread.start();
        // 3秒之后，生产一个包子
        Thread.sleep(3000L);
        resource = new Object();
        // 争取到锁以后，再恢复consumerThread
        synchronized (this) {
            LockSupport.unpark(consumerThread);
        }
        System.out.println("3、通知消费者");
    }

    public static void main(String[] args) throws Exception {
//        new Demo6().waitNotifyTest();
        new Demo6().parkUnparkTest();
    }
}
