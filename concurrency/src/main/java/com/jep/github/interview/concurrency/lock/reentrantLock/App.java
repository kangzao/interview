package com.jep.github.interview.concurrency.lock.reentrantLock;

/**
 * 比如调用 demo 方法获得了当前的对象锁，然后在这个方法中再去调用demo2，
 * demo2 中的存在同一个实例锁，这个时候当前线程会因为无法获得demo2 的对象锁而阻塞，就会产生死锁。
 * 重入锁的设计目的是避免线程的死锁。
 */
public class App {

  public synchronized void demo() { //main获得对象锁
    System.out.println("demo");
    demo2();
  }

  public void demo2() {
    synchronized (this) { //增加重入次数就行
      System.out.println("demo2");
    }//减少重入次数
  }

  public static void main(String[] args) {
    App app = new App();
    app.demo();
  }
}