package com.jep.github.interview.concurrency.completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class ThenApplyExample {
    public static void main(String[] args) throws InterruptedException {
// 创建一个异步计算任务
        System.out.println(Thread.currentThread().getName());

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
// 模拟耗时操作
            try {
                System.out.println(Thread.currentThread().getName());
                System.out.println(Thread.currentThread().isDaemon()); // 守护线程 精灵线程
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Hello";
        });
//        Thread.sleep(2000);

// 使用 thenApply 对结果进行转换
        CompletionStage<String> transformedFuture = future.thenApply(

                result -> {
                    System.out.println("tf---" + Thread.currentThread().getName());
                    return result + ", World!";
                }

        );//函数式编程  构造
// 获取转换后的结果
        transformedFuture.whenComplete((result, exception) -> {
            System.out.println("complete---" + Thread.currentThread().getName());
            if (exception == null) {
                System.out.println(result); // 输出 "Hello, World!"
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