package com.jep.github.interview.concurrency.park;

import java.util.concurrent.locks.LockSupport;


/*
 * @author: enping.jep
 * @create: 2021-05-12 4:09 下午
 */
public class ParkTest {

  public static void main(String args[]) throws InterruptedException {

    Thread thread = Thread.currentThread();
    System.out.println("第一次调用unpark");
    LockSupport.unpark(thread);
    System.out.println("第二次调用unpark");
    LockSupport.unpark(thread);
    System.out.println("第一次调用park");
    LockSupport.park();
    System.out.println("第二次调用park");
    LockSupport.park();
    System.out.println("执行完成");

  }

  public static void unpark_before_park() {
    Thread thread1 = new Thread(new Runnable() {
      @Override
      public void run() {
        System.out.println("thread1 start");

        try {
          Thread.sleep(2000);
        } catch (Exception e) {
          e.printStackTrace();
        }

        System.out.println("thread1 run before park");

        LockSupport.park();

        System.out.println("thread1 run after park");
      }
    });
    thread1.start();
    LockSupport.unpark(thread1);

    System.out.println("main thread");
  }


  public static void unpark_before_thread() {
    Thread thread1 = new Thread(new Runnable() {
      @Override
      public void run() {
        System.out.println("thread1 start");

        try {
          Thread.sleep(2000);
        } catch (Exception e) {
          e.printStackTrace();
        }

        System.out.println("thread1 run before park");

        LockSupport.park();

        System.out.println("thread1 run after park");
      }
    });
    //unpark必须在thread start之后才有用，之前调用没有任何效果:程序没有退出，线程1一直挂起
    LockSupport.unpark(thread1);
    thread1.start();
    System.out.println("main thread");


  }


  public static void unpark_after_park() throws InterruptedException {
    Thread thread1 = new Thread(new Runnable() {
      @Override
      public void run() {
        System.out.println("thread1 run before park");

        LockSupport.park();

        System.out.println("thread1 run after park");
      }
    });

    thread1.start();

    System.out.println("main thread");
    Thread.sleep(3000);
    LockSupport.unpark(thread1);

  }

}
