package com.jep.github.interview.jvm.stack;

import java.util.Date;

/**
 * 默认是1万多次 10823
 * 设置栈的大小-Xss256k 1874次
 **/
public class StackErrorTest {
    private static int count = 1;

    public static void main(String[] args) {
        System.out.println(count);
        count++;
        main(args);
    }

    public String test2(Date dateP, String name2) {
        dateP = null;
        name2 = "XIAOMING";
        double weight = 130.5; //占据两个slot
        return "";
    }
}