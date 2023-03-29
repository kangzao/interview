package com.jep.github.interview.concurrency.threadDemo;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

/*
 * @author: enping.jep
 * @create: 2023-03-23 3:08 PM
 * 验证对象结构  借助 openjdk 提供的 jol-core 进行打印分析
 */
public class JolDemo {

  public static void main(String[] args) {
    System.out.println(VM.current().details());
    Object obj = new Object();
    System.out.println(obj + " 十六进制哈希：" + Integer.toHexString(obj.hashCode()));
    System.out.println(ClassLayout.parseInstance(obj).toPrintable());
  }
}
