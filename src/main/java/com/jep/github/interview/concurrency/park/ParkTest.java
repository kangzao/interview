package com.jep.github.interview.concurrency.park;

import java.util.concurrent.locks.LockSupport;


/*
 * @author: enping.jep
 * @create: 2021-05-12 4:09 下午
 */
public class ParkTest {

  public static void main(String args[]) {
    test1();
  }


  public static void test1() {
    Thread thread = Thread.currentThread();
//    LockSupport.unpark(thread);//释放许可
    LockSupport.park();// 获取许可
    System.out.println("b");

  }

}
