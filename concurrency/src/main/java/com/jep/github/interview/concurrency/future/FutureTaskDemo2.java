package com.jep.github.interview.concurrency.future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class FutureTaskDemo2 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask futureTask = new FutureTask(() -> {
            System.out.println("-----come in FutureTask");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "" + ThreadLocalRandom.current().nextInt(100);
        });
        new Thread(futureTask, "t1").start();
        System.out.println(Thread.currentThread().getName() + "\t" + "线程完成任务");

        /**         * 用于阻塞式获取结果,如果想要异步获取结果,通常都会以轮询的方式去获取结果         */
        while (true) {
            if (futureTask.isDone()) {
                System.out.println(futureTask.get());//future.get是个阻塞方法，除了阻塞等待之外，还有第二种方式获取异步任务的执行结果：轮询
                break;
            }
        }
    }
}