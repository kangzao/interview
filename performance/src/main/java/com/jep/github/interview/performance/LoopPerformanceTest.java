package com.jep.github.interview.performance;

public class LoopPerformanceTest {
    static int foo() {
        int i = 0;
        while (i < 1000000000) {
            i++;
        }
        return i;
    }

    public static void main(String[] args) {
        // warmup
        for (int i = 0; i < 20000; i++) {
            foo();
        }
        // measurement
        long current = System.nanoTime();
        for (int i = 1; i <= 10000; i++) {
            foo();
            if (i % 1000 == 0) {
                long temp = System.nanoTime();
                //测试每次调用所花费的时间
                System.out.println(temp - current);
                current = System.nanoTime();
            }
        }
    }
}