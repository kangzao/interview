package com.jep.github.interview.concurrency.threadDemo;

public class ThreadException {

    public static void testUncaughtExceptionHandler() {
        Thread t = new Thread(() -> {
            System.out.println("===线程执行===");
            throw new RuntimeException("出现异常");
        });//初始化
        t.setName("test线程");
        t.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println(t.getName() + "执行, 异常信息:" + e.getMessage());
            }
        });
        t.start();
    }


    public static void main(String[] args) {
        testUncaughtExceptionHandler();
    }

}
