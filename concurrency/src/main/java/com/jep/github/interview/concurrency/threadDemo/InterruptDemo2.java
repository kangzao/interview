package com.jep.github.interview.concurrency.threadDemo;

import java.util.concurrent.TimeUnit;

/*
 * @author: enping.jep
 * @create: 2021-02-02 5:19 下午
 */
public class InterruptDemo2 {

  private static int i;

  public static void main(String[] args) throws InterruptedException {
    Thread thread = new Thread(new Runnable() {
      @Override
      public void run() {
        while (!Thread.currentThread().isInterrupted()) {//默认是false
          try {
            i++;
            TimeUnit.SECONDS.sleep(1);
            System.out.println("while - i:" + i);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
        System.out.println("i:" + i);//这里不会被执行到，try里面
      }
    });
    thread.start();
    TimeUnit.SECONDS.sleep(1);
    thread.interrupt();
    System.out.println(thread.isInterrupted());
  }

}
