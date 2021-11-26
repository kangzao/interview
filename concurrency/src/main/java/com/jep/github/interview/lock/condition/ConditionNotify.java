package com.jep.github.interview.lock.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class ConditionNotify implements Runnable {

  private Lock lock;
  private Condition condition;

  public ConditionNotify(Lock lock, Condition condition) {
    this.lock = lock;
    this.condition = condition;
  }

  @Override
  public void run() {
    try {
      lock.lock();//获得了锁.
      System.out.println("begin - conditionNotify");
      condition.signal();//唤醒阻塞状态的线程
      System.out.println("end - conditionNotify");
    } finally {
      lock.unlock(); //释放锁
    }
  }
}