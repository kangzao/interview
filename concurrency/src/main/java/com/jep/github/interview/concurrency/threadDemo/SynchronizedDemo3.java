package com.jep.github.interview.concurrency.threadDemo;

import java.io.IOException;

/**
 * Integer.valueOf中，如果参数值在一定范围内，就从IntegerCache缓存中返回，
 * 也就是说在一定范围内多个相同的值是同一个对象，超出的话则return new Integer(i)
 * 返回一个新的Integer(这个范围在-128-127),因为integer一直在变，
 * 线程每次加锁可能都加在了不同对象的实例上，导致临界区控制出现问题。
 * (不只Integer，Character、Short、Long也有缓存，但是Float和Double却没有)
 *
 */
public class SynchronizedDemo3 {

  static Integer count = 0;

  public static void incr() {
    synchronized (count) {
      try {
        Thread.sleep(1);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      count++;
    }
  }

  public static void main(String[] args) throws IOException, InterruptedException {
    for (int i = 0; i < 1000; i++) {
      new Thread(() -> SynchronizedDemo.incr()).start();
    }
    Thread.sleep(5000);
    System.out.println("result:" + count);
  }
}