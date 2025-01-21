package com.jep.github.interview.concurrency.completableFuture;

import ch.qos.logback.core.net.SyslogOutputStream;
import org.junit.Test;

import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.function.Supplier;

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

    // 测试嵌套的CompletableFuture
    @Test
    public void testNestedCf() throws ExecutionException, InterruptedException {
        // 创建一个CompletableFuture，异步执行一个任务，该任务返回1
        CompletableFuture<Integer> future1
                = CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @Override
            public Integer get() {
                System.out.println("compute 1");
                return 1;
            }
        });

        // 在future1完成后，使用thenApply方法继续执行一个异步任务，该任务的结果是一个新的CompletableFuture
        CompletableFuture<CompletableFuture<Integer>> future2
                = future1.thenApply(
                new Function<Integer, CompletableFuture<Integer>>() {
                    @Override
                    public CompletableFuture<Integer> apply(Integer r) {
                        return CompletableFuture.supplyAsync(() -> r + 10);
                    }
                });

        // 使用join方法获取future2的结果，然后再获取内部CompletableFuture的结果
        System.out.println(future2.join().join());
        // 使用get方法获取future2的结果，然后再获取内部CompletableFuture的结果
        System.out.println(future2.get().get());
    }

    @Test
    public void test9() throws Exception {
        // 创建异步执行任务:
        CompletableFuture<Double> cf = CompletableFuture.supplyAsync(() -> {
            // 打印当前线程信息，开始任务1
            System.out.println(Thread.currentThread() + " start job1,time->" + new Date());
            try {
                // 模拟任务1耗时2秒
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            // 打印当前线程信息，任务1结束
            System.out.println(Thread.currentThread() + " exit job1,time->" + new Date());
            // 返回任务1结果
            return 1.2;
        });
        // 当前任务完成后，将结果传递给下一个异步任务
        CompletableFuture<String> cf2 = cf.thenCompose((param) -> {
            // 打印当前线程信息，开始任务2
            System.out.println(Thread.currentThread() + " start job2,time->" + new Date());
            try {
                // 模拟任务2耗时2秒
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            // 打印当前线程信息，任务2结束
            System.out.println(Thread.currentThread() + " exit job2,time->" + new Date());
            // 任务2结束后，开启新的异步任务
            return CompletableFuture.supplyAsync(() -> {
                // 打印当前线程信息，开始任务3
                System.out.println(Thread.currentThread() + " start job3,time->" + new Date());
                try {
                    // 模拟任务3耗时2秒
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                }
                // 打印当前线程信息，任务3结束
                System.out.println(Thread.currentThread() + " exit job3,time->" + new Date());
                // 返回任务3结果
                return "job3 test";
            });
        });
        // 打印主线程开始获取任务结果的时间
        System.out.println("main thread start cf.get(),time->" + new Date());
        //等待子任务执行完成
        System.out.println("cf run result->" + cf.get());
        // 打印主线程开始获取第二个任务结果的时间
        System.out.println("main thread start cf2.get(),time->" + new Date());
        System.out.println("cf2 run result->" + cf2.get());
        // 打印主线程结束时间
        System.out.println("main thread exit,time->" + new Date());
    }

}
