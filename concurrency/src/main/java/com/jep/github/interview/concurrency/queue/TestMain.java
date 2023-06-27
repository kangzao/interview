package com.jep.github.interview.concurrency.queue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TestMain {
    //newSingleThread这个类型的线程池，等待队列是无界的
    static ExecutorService executorService = Executors.newSingleThreadExecutor();

    static class Task implements Runnable {
        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            executorService.execute(new Task());
        }
    }
}