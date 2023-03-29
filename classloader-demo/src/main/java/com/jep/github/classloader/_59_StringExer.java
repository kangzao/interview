package com.jep.github.classloader;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class _59_StringExer {

  String str = "good";

  public static final int n = 100;
  char[] ch = {'t', 'e', 's', 't'};

  public void change(String str, char ch[]) {
    str = "test ok";
    ch[0] = 'b';
  }

  public static void main(String[] args) {
    String str1 = new StringBuilder("计算机").append("软件").toString();
    System.out.println(str1.intern() == str1);
    String str2 = new StringBuilder("ja").append("va").toString();
    System.out.println(str2.intern() == str2);
  }
}