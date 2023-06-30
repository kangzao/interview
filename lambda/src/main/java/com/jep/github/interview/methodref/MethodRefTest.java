package com.jep.github.interview.methodref;

import java.util.function.Consumer;

public class MethodRefTest {
    /**
     * 情况一: 对象 :: 实例方法
     * Consumer 中的 void accept(T t)
     * PrintStream 中的 void println(T t)
     */
    public static void main(String[] args) {
        // 匿名实现接口的方式：
        Consumer<String> consumer1 = new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println("匿名实现接口的方式: " + s);
            }
        };

        consumer1.accept("Hello World");

        // Lambda 表达式
        Consumer<String> consumer2 = s -> System.out.println("Lambda 表达式: " + s);
        consumer2.accept("Hello World");

        // 方法引用
        Consumer<String> consumer = System.out::println;
        consumer.accept("Hello World");

    }
}