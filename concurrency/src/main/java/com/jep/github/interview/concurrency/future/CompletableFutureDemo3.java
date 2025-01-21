package com.jep.github.interview.concurrency.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class CompletableFutureDemo3 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println(CompletableFuture.supplyAsync(() -> {
            System.out.println("thread :" + Thread.currentThread().getName());
            return "abc";
        }).thenApply(r -> {
            System.out.println("apply thread :" + Thread.currentThread().getName());
            return r + "123";
        }).join());


        CompletableFuture.supplyAsync(() -> {
            return 1;
        }).thenApply(f -> {
            System.out.println("thenApply thread :" + Thread.currentThread().getName());
            return f + 2;
        }).thenApply(f -> {
            return f + 3;
        }).thenApply(f -> {
            return f + 4;
        }).thenAccept(r -> System.out.println(r));


        System.out.println(CompletableFuture.supplyAsync(() -> "resultA").thenRun(new Runnable() {
            @Override
            public void run() {
            }
        }).join());

        System.out.println(CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                return "resultA";
            }
        }).thenAccept(new Consumer<String>() {
            @Override
            public void accept(String resultA) {
            }
        }).join());

        System.out.println(CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                return "resultA";
            }
        }).thenApply(new Function<String, String>() {
            @Override
            public String apply(String resultA) {
                return resultA + " resultB";
            }
        }).join());
    }
}