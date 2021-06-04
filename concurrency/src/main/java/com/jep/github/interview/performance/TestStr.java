package com.jep.github.interview.performance;

import java.util.Random;

/*
 * @author: enping.jep
 * @create: 2021-06-03 10:25 上午
 */
public class TestStr {

  public static void main(String args[]) {
//    String s1 = new String("a") + new String("b"); //1
//    System.out.println(s1.intern() == s1); //2
//    s1 = null;
//    System.gc();
//    String s2 = new String("a") + new String("b"); //5
//    System.out.println(s2.intern() == s2); //6

    Random random = new Random();
    System.out.println(random.nextLong());
    while (true) {
      String s = String.valueOf(random.nextLong());
      s = s.intern();
    }
//    String str1 = "abc";
//    String str2 = new String("abc");
//    String str3 = str2.intern();
//    System.out.println(str1 == str2);
//    System.out.println(str2 == str3);
//    System.out.println(str1 == str3);


  }


}
