package com.jep.github.interview.jvm.stack;

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
}