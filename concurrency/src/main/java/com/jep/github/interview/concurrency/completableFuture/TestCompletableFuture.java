package com.jep.github.interview.concurrency.completableFuture;

import ch.qos.logback.core.net.SyslogOutputStream;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author enping.jep
 * @date 2024/03/14 16:12
 **/
public class TestCompletableFuture {

    @Test
    public void testCompletableThenCombine() throws ExecutionException, InterruptedException {
//创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(1);
//开启异步任务1
        CompletableFuture<Integer> task = CompletableFuture.supplyAsync(() -> {
            System.out.println("异步任务1，当前线程是：" + Thread.currentThread().getId());
            int result = 1 + 1;
            System.out.println("异步任务1结束");
            return result;
        }, executorService);
//开启异步任务2
        CompletableFuture<Integer> task2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("异步任务2，当前线程是：" + Thread.currentThread().getId());
            int result = 1 + 1;
            System.out.println("异步任务2结束");
            return result;
        }, executorService);
//任务组合
        CompletableFuture<Integer> task3 = task.thenCombineAsync(task2, (f1, f2) -> {
            System.out.println("执行任务3，当前线程是：" + Thread.currentThread().getId());
            System.out.println("任务1返回值：" + f1);
            System.out.println("任务2返回值：" + f2);
            return f1 + f2;
        }, executorService);
        Integer res = task3.get();
        System.out.println("最终结果：" + res);
    }

    @Test
    public void testCompletableEitherAsync() throws InterruptedException {
//创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(1);
//开启异步任务1
        CompletableFuture<Integer> task = CompletableFuture.supplyAsync(() -> {
            System.out.println("异步任务1，当前线程是：" + Thread.currentThread().getId());
            int result = 1 + 1;
            System.out.println("异步任务1结束");
            return result;
        }, executorService);
//开启异步任务2
        CompletableFuture<Integer> task2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("异步任务2，当前线程是：" + Thread.currentThread().getId());
            int result = 1 + 2;
            try {
                Thread.sleep(3000);
                System.out.println("sleep结束");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("异步任务2结束");
            return result;
        }, executorService);
//任务组合
        task.acceptEitherAsync(task2, (res) -> {
            System.out.println("执行任务3，当前线程是：" + Thread.currentThread().getId());
            System.out.println("上一个任务的结果为：" + res);
        }, executorService).whenComplete((res, e) -> {
            if (e != null) {
                e.printStackTrace();
            }
        });

        Thread.sleep(3000);
    }
//通过结果可以看出，异步任务2都没有执行结束，任务3获取的也是1的执行结果
    //如果把上面的核心线程数改为1 任务3不会执行，原因是单元测试JUnit@Test的测试 不支持多线程，代码执行完就会直接退出，不会检测子线程是否结束

    @Test
    public void testCompletableAllOf() throws ExecutionException, InterruptedException {
        //创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        //开启异步任务1
        CompletableFuture<Integer> task = CompletableFuture.supplyAsync(() -> {
            System.out.println("异步任务1，当前线程是：" + Thread.currentThread().getId());
            int result = 1 + 1;
            System.out.println("异步任务1结束");
            return result;
        }, executorService);

        //开启异步任务2
        CompletableFuture<Integer> task2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("异步任务2，当前线程是：" + Thread.currentThread().getId());
            int result = 1 + 2;
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("异步任务2结束");
            return result;
        }, executorService);

        //开启异步任务3
        CompletableFuture<Integer> task3 = CompletableFuture.supplyAsync(() -> {
            System.out.println("异步任务3，当前线程是：" + Thread.currentThread().getId());
            int result = 1 + 3;
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("异步任务3结束");
            return result;
        }, executorService);

        //任务组合  java.lang.Void是一种类型。例如给Void引用赋值null。
        CompletableFuture<Void> allOf = CompletableFuture.allOf(task, task2, task3);

        //等待所有任务完成
        allOf.get();
        //获取任务的返回结果
        System.out.println("task结果为：" + task.get());
        System.out.println("task2结果为：" + task2.get());
        System.out.println("task3结果为：" + task3.get());
    }

    @Test
    public void testCompletableAnyOf() throws ExecutionException, InterruptedException {
        //创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        //开启异步任务1
        CompletableFuture<Integer> task = CompletableFuture.supplyAsync(() -> {
            System.out.println("任务1 ==" + Thread.currentThread().getId());
            int result = 1 + 1;
            return result;
        }, executorService);

        //开启异步任务2
        CompletableFuture<Integer> task2 = CompletableFuture.supplyAsync(() -> {
            int result = 1 + 2;
            return result;
        }, executorService);

        //开启异步任务3
        CompletableFuture<Integer> task3 = CompletableFuture.supplyAsync(() -> {
            int result = 1 + 3;
            return result;
        }, executorService);

        //任务组合
        CompletableFuture<Object> anyOf = CompletableFuture.anyOf(task, task2, task3).whenComplete((res, e) -> {
            System.out.println("任务4 ==" + Thread.currentThread().getName());
        });
        //只要有一个有任务完成
        Object o = anyOf.get();
        System.out.println("完成的任务的结果：" + o);
    }


    @Test
    public void testWhenCompleteExceptionally() {
        CompletableFuture<Double> future = CompletableFuture.supplyAsync(() -> {
            if (1 == 1) {
                throw new RuntimeException("出错了");
            }
            return 0.11;
        });
        future.whenComplete((res, e) -> {
            if (e != null) {
                e.printStackTrace();
            }
        });

        //如果不加 get()方法或者不用whenComplete这一行，看不到异常信息
        //future.get();
    }
}
