package com.jep.github.interview.concurrency.threadDemo;

import java.util.concurrent.TimeUnit;

/**
 * 使用jps查看所有java进程
 * 再使用jstack pid查看线程的状态
 */

public class ThreadStatusDemo {

  public static void main(String[] args) {
    new Thread(() -> {
      while (true) {
        try {
          TimeUnit.SECONDS.sleep(100);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }, "Time_Waiting_Thread").start();// TIMED_WAITING (sleeping)

    new Thread(() -> {
      while (true) {
        synchronized (ThreadStatusDemo.class) {
          try {
            ThreadStatusDemo.class.wait();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    }, "Wating_Thread").start();//WAITING (on object monitor)

    new Thread(new BlockedDemo(), "Blocke01_Thread").start();//TIMED_WAITING (sleeping)
    new Thread(new BlockedDemo(), "Blocke02_Thread").start();//BLOCKED (on object monitor)
  }

  static class BlockedDemo extends Thread {

    @Override
    public void run() {
      synchronized (BlockedDemo.class) {
        while (true) {
          try {
            TimeUnit.SECONDS.sleep(100);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    }
  }
}