package com.jep.github.interview.concurrency.queue;

import java.util.concurrent.LinkedBlockingQueue;

public class ProducerConsumerExample {
    public static void main(String[] args) {
// 创建一个任务队列
        LinkedBlockingQueue<Integer> taskQueue = new LinkedBlockingQueue<>(10);
// 创建一个生产者线程
        Thread producerThread = new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                try {
                    taskQueue.put(i);
                    System.out.println("Produced: " + i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
// 创建一个消费者线程
        Thread consumerThread = new Thread(() -> {
            while (true) {
                try {
                    int task = taskQueue.take();
                    System.out.println("Consumed: " + task);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
// 启动生产者和消费者线程
        producerThread.start();
        consumerThread.start();
    }
}