package com.jep.github.interview.concurrency.completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;

public class CompletableFutureExample {
    public static void main(String[] args) {
        System.out.println("Starting Completable Future Example..");
        //创建异步任务
        CompletableFuture<String> basePrep = CompletableFuture.supplyAsync(() -> {
            System.out.println("basePrep:" + Thread.currentThread().getName());
            System.out.println("Preparing Base");
            return "Base Ready";
        });
        //创建异步任务
        CompletableFuture<String> topPrep = CompletableFuture.supplyAsync(() -> {
            System.out.println("topPrep:" + Thread.currentThread().getName());
            System.out.println("Preparing Top");
            return "Toppings Ready";
        });

        System.out.println(Thread.currentThread().getName());
//        执行两个独立的任务，并对其结果执行操作  baseResponse是basePrep的结果  topResponse是topPrep的结果
        CompletableFuture<String> foodPrep = basePrep.thenCombine(topPrep, (baseResponse, topResponse) -> {
            System.out.println("foodPrep=" + Thread.currentThread().getName());
            System.out.println("Top and Base are::" + baseResponse + ":" + topResponse);
            return "Pizza Ready";
        });
        //food作为foodPrep的结果，给函数体使用(是函数的入参)，函数体返回一个新的异步任务
        CompletableFuture<String> foodServe = foodPrep.thenCompose(food -> {
            System.out.println("food:" + food);
            System.out.println("foodServe:" + Thread.currentThread().getName());
            System.out.println("Serving Food");
            //需要返回一个新的异步任务
            CompletableFuture<String> xx = CompletableFuture.supplyAsync(() -> {
                System.out.println("foodServe-ASYNC:" + Thread.currentThread().getName());
                System.out.println(Thread.currentThread().getName());
                return food + "：Food Served";
            });
            System.out.println("xx -----:" + xx);//fde0
            return xx;
        });
        System.out.println("foodServe -----:" + foodServe);//b3d
        //把foodServe的结果进行了消费  order消费foodServe的返回值
        CompletableFuture<String> order = foodServe.thenApply(orderComplete -> {
            System.out.println("parameter foodServe -----:" + foodServe);//b3d
            System.out.println("orderComplete:" + orderComplete);
            System.out.println("orderThread == " + Thread.currentThread().getName());
            return orderComplete + "：Order Completed..";
        });
        try {
            System.out.println(order.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}