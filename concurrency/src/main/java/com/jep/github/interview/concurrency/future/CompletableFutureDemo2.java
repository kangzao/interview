package com.jep.github.interview.concurrency.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class CompletableFutureDemo2 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture completableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println(Thread.currentThread().getName());
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 533;
        });

//        去掉注释后上面代码计算完成，返回结果
//        try {
////            TimeUnit.SECONDS.sleep(2);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        System.out.println(completableFuture.getNow(444));
    }
}