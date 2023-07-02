package com.jep.github.interview.concurrency.threadDemo;

/*
 * @author: enping.jep
 * @create: 2021-02-16 9:56 上午
 */
public class VolatileDemo {

    public volatile static boolean stop = false;//不加volatile，程序停不下来

    public static void main(String args[]) throws InterruptedException {
        Thread thread = new Thread(() -> {
            int i = 0;
            while (!stop) {
                i++;
                System.out.println("i=" + i);
            }
        });
        thread.start();
        System.out.println("begin start thread");
        Thread.sleep(1000);
        stop = true;

    }


}
