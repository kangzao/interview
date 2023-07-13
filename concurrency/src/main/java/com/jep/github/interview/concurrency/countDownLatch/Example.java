package com.jep.github.interview.concurrency.countDownLatch;

import java.util.concurrent.CountDownLatch;

public class Example {
    public static void main(String[] args) throws InterruptedException {

        int numTasks = 5;
        CountDownLatch latch = new CountDownLatch(5);
        for (int i = 0; i < numTasks; i++) {
            new Thread(() -> {
// 子任务的逻辑
                latch.countDown();
                //超时的操作
                System.out.println("子任务完成");

            }).start();
        }
// 主线程等待所有子任务完成
        latch.await();
// 所有子任务完成后执行主任务的逻辑
        System.out.println("所有子任务完成，执行主任务");
    }
}