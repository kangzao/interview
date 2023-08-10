package com.jep.github.interview.concurrency.threadDemo;

import java.util.concurrent.Callable;

public class CallableTask implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        // do something
        Thread.sleep(2000);

        return 100;
    }
}