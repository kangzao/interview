package com.jep.github.interview.concurrency.future;

import java.util.concurrent.*;

public class FutureTaskExample {

    public static void main(String[] args) throws ExecutionException {
        TestCallable callable1 = new TestCallable(1000);
        TestCallable callable2 = new TestCallable(2000);

        FutureTask<String> futureTask1 = new FutureTask<String>(callable1);
        FutureTask<String> futureTask2 = new FutureTask<String>(callable2);

        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.execute(futureTask1);
        executor.execute(futureTask2);//提交到线程池以后，两个线程分别沉睡1s和2s

        while (true) {
            try {
                if (futureTask1.isDone() && futureTask2.isDone()) {
                    System.out.println("Done");
                    //shut down executor service
                    executor.shutdown();
                    return;
                }

                if (!futureTask1.isDone()) {
                    //wait indefinitely for future task to complete
                    System.out.println("FutureTask1 output=" + futureTask1.get());//不见不散 当任务1完成时  说明已经过去了一秒且任务1执行完毕  且 任务2 也沉睡了一秒
                }

                System.out.println("Waiting for FutureTask2 to complete");
                String s = futureTask2.get(200L, TimeUnit.MILLISECONDS);//过期不候  第一次走到这里 任务2还需要沉睡1秒 第二次：400ms 200ms  1s   意味着等待超时代码 执行五次
                //无法判断是否获取到了结果，超时说明：s == null
                if (s != null) {
                    //不超时，输出s的值
                    System.out.println("FutureTask2 output=" + s);
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                //do nothing
            }
        }
    }
}

class TestCallable implements Callable<String> {

    private final long waitTime;

    public TestCallable(int timeInMillis) {
        this.waitTime = timeInMillis;
    }

    @Override
    public String call() throws Exception {
        Thread.sleep(waitTime);
        //return the thread name executing this callable task
        return Thread.currentThread().getName();
    }
}