package com.jep.github.interview.concurrency.threadDemo;

/*
 * @author: enping.jep
 * @create: 2021-02-16 9:56 上午
 */
public class VolatileDemo {

  public volatile static boolean stop = false;

  public static void main(String args[]) throws InterruptedException {
    Thread thread = new Thread(() -> {
      int i = 0;
      while (!stop) {
        i++;
      }
    });
    thread.start();
    System.out.println("begin start thread");
    Thread.sleep(1000);
    stop = true;

  }

}
