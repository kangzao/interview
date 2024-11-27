package com.jep.github.interview.concurrency.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class CompletableFutureDemo5 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //当一个线程依赖另一个线程时用 handle 方法来把这两个线程串行化,        
        // 异常情况：有异常也可以往下一步走，根据带的异常参数可以进一步处理
        CompletableFuture.supplyAsync(() -> {
            //暂停几秒钟线程
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("111");
            return 1024;
        }).handle((f, e) -> {
            System.out.println("f ==" + f + ", e ==" + e);
            System.out.println(Thread.currentThread().getName() + "==step 1");
            int age = 10 / 0;
            System.out.println("222");
            return f + 1;
        }).handle((f, e) -> {
            System.out.println(Thread.currentThread().getName() + "==step 2");
            System.out.println("333");
            return f + 1;
        }).whenCompleteAsync((v, e) -> {
            System.out.println(Thread.currentThread().getName() + "==step 3");
            System.out.println("*****v: " + v);
        }).exceptionally(e -> {
            System.out.println(Thread.currentThread().getName() + "==step 4");
            e.printStackTrace();
            return null;
        });
        System.out.println("-----主线程结束，END");
// 主线程不要立刻结束，否则CompletableFuture默认使用的线程池会立刻关闭:
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}