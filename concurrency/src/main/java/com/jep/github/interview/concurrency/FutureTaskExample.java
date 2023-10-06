package com.jep.github.interview.concurrency;

import java.util.concurrent.*;

public class FutureTaskExample {
    public static void main(String[] args) throws ExecutionException {
        TestCallable callable1 = new TestCallable(1000);
        TestCallable callable2 = new TestCallable(2000);
        FutureTask<String> futureTask1 = new FutureTask<String>(callable1);
        FutureTask<String> futureTask2 = new FutureTask<String>(callable2);
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.execute(futureTask1);//task1 sleep 1s 然后返回线程名字
        executor.execute(futureTask2);//task1 sleep 2s 然后返回线程名字
        while (true) {
            try {
                if (futureTask1.isDone() && futureTask2.isDone()) {//任务1 和 任务2是否同时执行完毕
                    System.out.println("Done");
                    //shut down executor service
                    executor.shutdown();
                    return;
                }
                if (!futureTask1.isDone()) {
                    //wait indefinitely for future task to complete
                    System.out.println("FutureTask1 output=" + futureTask1.get());
                }
                System.out.println("Waiting for FutureTask2 to complete");//过去一秒 1.2s
                String s = futureTask2.get(200L, TimeUnit.MILLISECONDS); //任务2还有一秒钟才苏醒 等五次知道苏醒
                if (s != null) {  //1、1S 2、800ms  3、600ms  4、400ms 5、200ms 
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