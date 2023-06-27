package com.jep.github.interview.concurrency.completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CompletableFutureDemo {
    public Future<String> calculateAsync() throws InterruptedException {
        CompletableFuture<String> completableFutureexample = new CompletableFuture<>();

        Executors.newCachedThreadPool().submit(() -> {
            Thread.sleep(500);
            completableFutureexample.complete("Hello");
            return null;
        });

        return completableFutureexample;
    }

    public static void main(String[] args) {
        CompletableFutureDemo demo = new CompletableFutureDemo();
        try {
            Future<String> completableFuture = demo.calculateAsync();
            long t1 = System.currentTimeMillis();
            System.out.println("current time:" + t1);
            String result = completableFuture.get();
            System.out.println(result);
            long t2 = System.currentTimeMillis();
            System.out.println("current time:" + t2);
            System.out.println("tim required : " + (t2 - t1));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}  
