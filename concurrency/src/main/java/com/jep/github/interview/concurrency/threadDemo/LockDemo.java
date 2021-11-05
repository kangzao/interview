package com.jep.github.interview.concurrency.threadDemo;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/*
 * @author: enping.jep
 * @create: 2021-02-17 6:20 下午
 * 读写锁的demo
 */
public class LockDemo {

  static Map<String, Object> cacheMap = new HashMap<>();
  static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

  static Lock read = rwl.readLock();
  static Lock write = rwl.writeLock();

  public static final Object get(String key) {
    System.out.println("read data");
    read.lock();
    try {
      return cacheMap.get(key);
    } finally {
      read.unlock();
    }
  }

  public static void put(String key, Object value) {
    write.lock();
    System.out.println("put data");
    try {
      cacheMap.put(key, value);
    } finally {
      write.unlock();
    }
  }

  public static void main(String args[]) {

  }


}
