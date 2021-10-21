package com.jep.github.interview.concurrency.notify;

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
        lock.wait(); //实现线程的阻塞
        System.out.println("Causes the current thread to wait until another thread invokes the\n"
            + "     * {@link java.lang.Object#notify()} method or the\n"
            + "     * {@link java.lang.Object#notifyAll()} method for this object");
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println("end ThreadA");
    }
  }
}