package com.jep.github.interview.concurrency.completableFuture;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


@Slf4j
public class CompletableFutureDemo {
    public Future<String> calculateAsync() throws InterruptedException {
        CompletableFuture<String> cf = new CompletableFuture<>();

        Executors.newCachedThreadPool().submit(() -> {
            Thread.sleep(500);
            cf.complete("Hello");
            return null;
        });

        return cf;
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

        CompletableFuture<String> baseFuture = CompletableFuture.completedFuture("Base Future");
        log.info(baseFuture.thenApply((r) -> r + " Then Apply").join());
        baseFuture.thenAccept((r) -> log.info(r)).thenAccept((Void) -> log.info("Void"));

    }
}  
