package com.jep.github.classloader;

public class Test {

    public Test() {

    }git s

    public static void main(String[] args) {

        long start = System.currentTimeMillis();
        for (long i = 0; i < 1000000; i++) {

            StackTraceElement[] stackTrace;

            if (args.length > 0)

                stackTrace = new Exception().getStackTrace();

            else
                stackTrace = Thread.currentThread().getStackTrace();
//                System.out.println("test");

        }

        System.out.println("Total time = " + (System.currentTimeMillis()
                - start) + "ms.");

    }
}