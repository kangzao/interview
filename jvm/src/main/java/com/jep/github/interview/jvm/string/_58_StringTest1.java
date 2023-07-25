package com.jep.github.interview.jvm.string;

import org.junit.Test;

public class _58_StringTest1 {

    @Test
    public void test1() {
        String s1 = "abc";//字面量定义的方式，"abc"存储在字符串常量池中
        String s2 = "abc";
        // s1指向的对象是abc字符串对象，修改s1会重新new一个新的字符串对象hello，然后修改s1的引用指向新的对象
        s1 = "hello";

        System.out.println(s1 == s2);//判断地址：false

        System.out.println(s1);// hello
        System.out.println(s2);//abc

    }

    @Test
    public void test2() {
        String s1 = "abc";
        String s2 = "abc";
        // 要改s2就要新new一个对象，修改s2的引用指向
        s2 += "def";
        System.out.println(s2);//abcdef
        System.out.println(s1);//abc
    }

    @Test
    public void test3() {
        String s1 = "abc";
        String s2 = s1.replace('a', 'm');
        System.out.println(s1);//abc
        System.out.println(s2);//mbc
    }
}