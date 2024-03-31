package com.jep.github.interview.concurrency.threadDemo;

import java.util.Comparator;
import java.util.function.Consumer;

/*
 * @author: enping.jep
 * @create: 2021-02-02 3:33 下午
 * 继承Thread类启动线程
 */
public class MyThread extends Thread {

    public void run() {
        System.out.println("MyThread run");
    }

    public static void main(String args[]) {
        MyThread thread1 = new MyThread();
        MyThread thread2 = new MyThread();
        thread1.start();
        thread2.start();


//        Consumer<String> consumer = (String s) -> System.out.println(s);


        Consumer<String> consumer = s -> System.out.println(s);
        consumer.accept("你好"); // 调用接口中重写的抽象方法

        Comparator<Integer> comparator = (o1, o2) -> {
            System.out.println(o1);
            System.out.println(o2);
            return o1.compareTo(o2);
        };

        Comparator<Integer> comparator1 = (o1, o2) -> {
            System.out.println(o1);
            System.out.println(o2);
            return o1.compareTo(o2);
        };

        System.out.println(comparator1.compare(1, 2));


    }

}
