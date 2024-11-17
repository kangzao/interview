package com.jep.github.interview.concurrency.threadDemo;

public class ThreaCounter extends Thread {
    //类变量  生命周期不依赖于对象
    private static int cnt = 0;

    @Override
    public void run() {
        for (int i = 0; i < 100000; i++) {
            cnt++;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreaCounter t1 = new ThreaCounter();
        System.out.println(t1.toString());
        ThreaCounter t2 = new ThreaCounter();
        ThreaCounter t3 = new ThreaCounter();//语言级别有了线程 操作系统还没有
        t1.start();//意味着操作系统中有了线程  是否run起来由操作系统决定
        t2.start();
        t3.start();
        t1.join();//主线程挂起等待 直到t1执行完
        t2.join();
        t3.join();
        System.out.println("cnt is " + cnt);
    }
}