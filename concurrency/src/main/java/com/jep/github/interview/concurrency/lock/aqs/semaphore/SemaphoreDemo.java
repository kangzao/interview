package com.jep.github.interview.concurrency.lock.aqs.semaphore;

import java.util.Random;

/**
 * @author enping
 * @date 2021/4/25 下午8:25
 **/
public class SemaphoreDemo {

    public static void main(String[] args) {
        SemaphoreDemo semaphoreTest = new SemaphoreDemo();
        int N = 9;            // 客人数量
        NeteaseSemaphore semaphore = new NeteaseSemaphore(5); // 手牌数量，限制请求数量
        for (int i = 0; i < N; i++) {
            String vipNo = "vip-00" + i;
            new Thread(() -> {
                try {
                    semaphore.acquire(); // 获取令牌

                    semaphoreTest.service(vipNo);

                    semaphore.release(); // 释放令牌
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    // 限流 控制5个线程 同时访问
    public void service(String vipNo) throws InterruptedException {
        System.out.println("楼上出来迎接贵宾一位，贵宾编号" + vipNo + "，...");
        Thread.sleep(new Random().nextInt(3000));
        System.out.println("欢送贵宾出门，贵宾编号" + vipNo);
    }

}
