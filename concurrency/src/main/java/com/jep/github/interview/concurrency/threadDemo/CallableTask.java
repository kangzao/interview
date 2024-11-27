package com.jep.github.interview.concurrency.threadDemo;

import java.util.concurrent.Callable;

public class CallableTask implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        // do something
        Thread.sleep(2000);
        System.out.println("com.jep.github.interview.concurrency.threadDemo.CallableTask.call()");
        return 100;
    }
}