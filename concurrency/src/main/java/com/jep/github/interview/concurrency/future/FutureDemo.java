package com.jep.github.interview.concurrency.future;

import java.util.concurrent.*;

public class FutureDemo {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future future = executorService.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                Long start = System.currentTimeMillis();
                while (true) {
                    Long current = System.currentTimeMillis();
                    if ((current - start) > 1000) {
                        return 1;
                    }
                }
            }
        });

        try {
            Integer result = (Integer) future.get(500, TimeUnit.MILLISECONDS);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
