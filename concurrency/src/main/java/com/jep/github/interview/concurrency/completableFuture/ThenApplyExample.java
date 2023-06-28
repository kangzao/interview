package com.jep.github.interview.concurrency.completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class ThenApplyExample {
    public static void main(String[] args) {
// 创建一个异步计算任务
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
// 模拟耗时操作
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Hello";
        });
// 使用 thenApply 对结果进行转换
        CompletionStage<String> transformedFuture = future.thenApply(result -> result + ", World!");//函数式编程  构造
// 获取转换后的结果
        transformedFuture.whenComplete((result, exception) -> {
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