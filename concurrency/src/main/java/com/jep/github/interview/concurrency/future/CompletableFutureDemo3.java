package com.jep.github.interview.concurrency.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureDemo3 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println(CompletableFuture.supplyAsync(() -> "abc").thenApply(r -> r + "123").join());


        CompletableFuture.supplyAsync(() -> {
            return 1;
        }).thenApply(f -> {
            return f + 2;
        }).thenApply(f -> {
            return f + 3;
        }).thenApply(f -> {
            return f + 4;
        }).thenAccept(r -> System.out.println(r));


        System.out.println(CompletableFuture.supplyAsync(() -> "resultA").thenRun(() -> {
        }).join());
        System.out.println(CompletableFuture.supplyAsync(() -> "resultA").thenAccept(resultA -> {
        }).join());
        System.out.println(CompletableFuture.supplyAsync(() -> "resultA").thenApply(resultA -> resultA + " resultB").join());
    }
}