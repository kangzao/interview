package com.jep.github.interview.concurrency.threadDemo;

public class SynchronizedDemo1 implements Runnable {

  int x = 100;

  public synchronized void m1() {
    x = 1000;
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("into m1()");
    System.out.println("x=" + x);
  }

  public synchronized void m2() {
    try {
      Thread.sleep(200);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("into m2()");
    x = 2000;
  }

  public static void main(String[] args) throws InterruptedException {
    SynchronizedDemo1 sd = new SynchronizedDemo1();
    new Thread(() -> sd.m1()).start();
    new Thread(() -> sd.m2()).start();
    sd.m2();
    System.out.println("Main x=" + sd.x);
  }

  @Override
  public void run() {
    System.out.println("run");
    m1();
  }

  /**
   * m1 sleep 1000，thread2里的m2阻塞，
   * main里的m2阻塞，m1返回，x=1000，
   * main的m2或者thread2的m2拿到锁，然后System打印
   */
}