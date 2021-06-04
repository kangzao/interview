package com.jep.github.interview.concurrency.threadDemo;

/*
 * @author: enping.jep
 * @create: 2021-02-17 6:09 下午
 */
public class ReentrantDemo {

  public synchronized void demo() {
    System.out.println("demo");
    demo2();

  }

  public void demo2() {
    System.out.println("demo2");
    synchronized (this) {

    }
  }

  public static void main(String args[]) {
    ReentrantDemo reentrantDemo = new ReentrantDemo();
    new Thread(reentrantDemo::demo).start();
  }
}
