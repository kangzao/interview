package com.jep.github.classloader;

import java.util.ArrayList;
import java.util.List;
/**
 * 测试MinorGC 、 MajorGC、FullGC
 * -Xms9m -Xmx9m -XX:+PrintGCDetails
 * 字符串常量池在堆空间中
 */
public class GCTest {
  public static void main(String[] args) {
    int i = 0;
    try {
      List<String> list = new ArrayList<>();
      String a = "atguigu.com";
      while (true) {
        list.add(a);
        a = a + a;
        i++;
      }

    } catch (Throwable t) {
      t.printStackTrace();
      System.out.println("遍历次数为：" + i);
    }
  }
}