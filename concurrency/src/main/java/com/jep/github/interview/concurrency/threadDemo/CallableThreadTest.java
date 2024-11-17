package com.jep.github.interview.concurrency.threadDemo;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class CallableThreadTest {
    public static void main(String[] args) {
        // FutureTask是一个包装器，通过接收Callable来创建，它同时实现了Future和Runnable接口。
        FutureTask<Integer> f = new FutureTask<>(new CallableTask());//异步线程  可以返回结果

        // 使用FutureTask对象作为Thread对象的target创建并启动新线程
        new Thread(f, "有返回值的线程").start();//多态 子类当做父类用

        try {
            System.out.println("子线程的返回值：");
            // 调用FutureTask对象的get()方法来同步等待子线程执行结束后的返回值  //不见不散
            System.out.println("子线程的返回值---------：" + f.get(3000, TimeUnit.MILLISECONDS));
        } catch (Exception e) {

        }
    }
}

