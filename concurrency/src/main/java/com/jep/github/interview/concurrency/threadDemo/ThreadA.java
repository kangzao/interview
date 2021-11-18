package com.jep.github.interview.concurrency.threadDemo;

/**
 * wait notify演示
 */
public class ThreadA extends Thread {

  private Object lock;

  public ThreadA(Object lock) {
    this.lock = lock;
  }

  @Override
  public void run() {
    synchronized (lock) {
      System.out.println("start ThreadA");
      try {
        lock.wait(); //实现线程的阻塞并释放锁
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println("end ThreadA");
    }
  }
}