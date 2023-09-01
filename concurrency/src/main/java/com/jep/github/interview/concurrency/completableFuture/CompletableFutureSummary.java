package com.jep.github.interview.concurrency.completableFuture;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.*;

import static org.junit.Assert.*;

/**
 * @author enping.jep
 * @date 2023/08/29 17:37
 **/
public class CompletableFutureSummary {

    static ExecutorService executor = Executors.newFixedThreadPool(3, new ThreadFactory() {
        int count = 1;

        @Override
        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, "custom-executor-" + count++);
        }
    });


    /**
     * 使用一个预定义的结果创建一个完成的CompletableFuture,通常我们会在计算的开始阶段使用它。
     * getNow(null)方法在future完成的情况下会返回结果，否则返回null (传入的参数)。
     */
    @Test
    public void completedFutureExample() {
        CompletableFuture cf = CompletableFuture.completedFuture("message");
        System.out.println(cf.isDone());
        System.out.println(cf.getNow(null));
    }

    /**
     * 运行一个简单的异步阶段
     * CompletableFuture的方法如果以Async结尾，它会异步的执行(没有指定executor的情况下)，
     * 异步执行通过ForkJoinPool实现， 它使用守护线程去执行任务。
     * <p>
     * true
     * 执行线程：ForkJoinPool.commonPool-worker-1
     * true
     */
    @Test
    public void runAsyncExample() {
        CompletableFuture cf = CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().isDaemon());
            System.out.println("执行线程：" + Thread.currentThread().getName());
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(cf.isDone());
    }


    /**
     * then意味着这个阶段的动作发生当前的阶段正常完成之后。本例中，当前节点完成，返回字符串message。
     * Apply意味着返回的阶段将会对结果前一阶段的结果应用一个函数。
     * <p>
     * 执行线程：main
     * false
     * MESSAGE
     */
    @Test
    public void thenApplyExample() {
        CompletableFuture cf = CompletableFuture.completedFuture("message").thenApply(s -> {
            System.out.println("执行线程：" + Thread.currentThread().getName());
            System.out.println(Thread.currentThread().isDaemon());
            return s.toUpperCase();
        });
        System.out.println(cf.getNow(null));
    }


    /**
     * 通过调用异步方法(方法后边加Async后缀)，串联起来的CompletableFuture可以异步地执行（使用ForkJoinPool.commonPool()）
     */
    @Test
    public void thenApplyAsyncExample() throws InterruptedException {
        CompletableFuture cf = CompletableFuture.completedFuture("message").thenApplyAsync(s -> {
            System.out.println("执行线程：" + Thread.currentThread().getName());
            System.out.println(Thread.currentThread().isDaemon());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return s.toUpperCase();
        });
//        Thread.sleep(2000);
        System.out.println(cf.getNow(null));
        System.out.println(cf.join());
    }


    /**
     * 提供一个Executor来异步地执行CompletableFuture
     */
    @Test
    public void thenApplyAsyncWithExecutorExample() {
        CompletableFuture cf = CompletableFuture.completedFuture("message").thenApplyAsync(s -> {
            System.out.println(Thread.currentThread().getName());
            System.out.println(Thread.currentThread().isDaemon());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return s.toUpperCase();
        }, executor);
        System.out.println(cf.getNow(null));
        System.out.println(cf.join());
    }

    /**
     * 消费前一阶段的结果
     * 下一阶段接收了当前阶段的结果，但是在计算的时候不需要返回值(它的返回类型是void)
     */
    @Test
    public void thenAcceptExample() {
        StringBuilder result = new StringBuilder();
        CompletableFuture.completedFuture("thenAccept message")
                .thenAccept(s -> {
                    result.append(s);
                    System.out.println(Thread.currentThread().getName());
                    System.out.println(Thread.currentThread().isDaemon());
                });
        System.out.println(result);
    }


    /**
     * 异步地消费结果
     */
    @Test
    public void thenAcceptAsyncExample() {
        StringBuilder result = new StringBuilder();
        CompletableFuture cf = CompletableFuture.completedFuture("thenAcceptAsync message")
                .thenAcceptAsync(s -> {
                    result.append(s);
                    System.out.println("执行线程：" + Thread.currentThread().getName());
                    System.out.println(Thread.currentThread().isDaemon());
                });


        cf.join();
        System.out.println(result);//放在join后面才会有输出
    }

    /**
     * 异常处理
     */
    @Test
    public void completeExceptionallyExample() {
        //双冒号，左边是类，右边是类中的方法。
        CompletableFuture cf = CompletableFuture.completedFuture("message").thenApplyAsync(String::toUpperCase);
        CompletableFuture exceptionHandler = cf.handle((s, th) -> {
            return (th != null) ? "message upon cancel" : "";
        });
        cf.completeExceptionally(new RuntimeException("completed exceptionally"));
//        assertTrue("Was not completed exceptionally", cf.isCompletedExceptionally());
        try {
            cf.join();
            fail("Should have thrown an exception");
        } catch (CompletionException ex) { // just for testing
            assertEquals("completed exceptionally", ex.getCause().getMessage());
        }

        assertEquals("message upon cancel", exceptionHandler.join());
    }
}
