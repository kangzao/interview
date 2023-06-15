package com.jep.github.interview.concurrency.threadDemo;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/*
 * @author: enping.jep
 * @create: 2021-02-02 3:53 下午
 * 实现callable接口通过futureTask包装器来创建Thread线程
 */
public class CallableDemo implements Callable {

    @Override
    public Object call() throws Exception {
        wait();
        int a = 1;
        int b = 2;
        return "计算结果：" + (a + b);
    }

    public static void main(String args[]) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future<String> furure = executorService.submit(new CallableDemo());
        System.out.println(furure.get());
        executorService.shutdown();


    }
}
