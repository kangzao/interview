package com.jep.github.interview.concurrency.completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class ThenAcceptExample {
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName());
// 创建一个异步计算任务
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName());
// 模拟耗时操作
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Hello";
        });
// 使用 thenAccept 对结果进行消费
        CompletionStage<Void> consumedFuture = future.thenAccept(result -> {
            System.out.println(Thread.currentThread().getName());
            System.out.println(result + ", World!");
        });
// 等待异步任务完成
        consumedFuture.whenComplete((result, exception) -> {
            System.out.println(Thread.currentThread().getName());
            if (exception == null) {
                System.out.println("Consumed successfully!");
            } else {
                System.out.println("Error: " + exception.getMessage());
            }
        });
// 阻止主线程退出，以便异步任务完成
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}