package com.jep.github.interview.concurrency.basic;

public class ThreaCounter extends Thread {
    private static int cnt = 0;

    @Override
    public void run() {
        for (int i = 0; i < 100000; i++) {
            cnt++;// 1 2 3 读到中间态数据 引发不可知的情况，结果不确定
            //100000
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreaCounter t1 = new ThreaCounter();
        ThreaCounter t2 = new ThreaCounter();
        ThreaCounter t3 = new ThreaCounter();
        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();
        System.out.println("cnt is " + cnt);
        //编译优化 bytecode  指令重排序
    }
    //main函数唤起一个jvm进程  包含三个线程  三个线程各自运行  有个类变量(被多个线程访问)
}