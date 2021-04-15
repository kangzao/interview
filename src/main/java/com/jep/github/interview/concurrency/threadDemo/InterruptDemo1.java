package com.jep.github.interview.concurrency.threadDemo;

import java.util.concurrent.TimeUnit;

/*
 * @author: enping.jep
 * @create: 2021-02-02 5:19 下午
 */
public class InterruptDemo1 {

  private static int i;

  public static void main(String[] args) throws InterruptedException {
    Thread thread = new Thread(() -> {
      while (true) {
        //通过 isInterrupted()来判断是否被中断
        if (Thread.currentThread().isInterrupted()) {
          System.out.println("before:" + Thread.currentThread().isInterrupted());
          Thread.interrupted(); //对线程进行复位，由 true 变成 false
          System.out.println("after:" + Thread.currentThread().isInterrupted());
        }
      }
    }, "interruptDemo");
    thread.start();
    TimeUnit.SECONDS.sleep(1);
    thread.interrupt();
  }
}