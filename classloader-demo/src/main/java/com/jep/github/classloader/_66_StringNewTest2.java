package com.jep.github.classloader;

/**
 * 思考： new String("a") + new String("b")会创建几个对象？  对象1：new StringBuilder()，用来append(a) append(b) 对象2：
 * new String("a")  对象3： 常量池中的"a"  对象4： new String("b")  对象5： 常量池中的"b"
 *
 * 深入剖析： StringBuilder的toString():    对象6 ：new String("ab") -- （SB的toString()的底层就是new一个新的）
 * 强调一下，toString()的调用，在字符串常量池中，没有生成"ab"
 */
public class _66_StringNewTest2 {

  public static void main(String[] args) {
    String str = new String("a") + new String("b");
  }
}