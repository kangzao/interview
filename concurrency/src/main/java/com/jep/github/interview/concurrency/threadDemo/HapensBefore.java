package com.jep.github.interview.concurrency.threadDemo;

public class HapensBefore {
    private static int var = 66;


    private static void startRule() {
        Thread B = new Thread(() -> {
            // 主线程调用B.start()之前
            // 所有对共享变量的修改，此处皆可见
            // 此例中，var==77
            System.out.println(var);
        });
// 此处对共享变量var修改
        var = 77;
// 主线程启动子线程
        B.start();
    }

    private static void joinRule() {
        Thread B = new Thread(() -> {
            // 此处对共享变量var修改
            var = 77;
        });
        // 例如此处对共享变量修改，
        // 则这个修改结果对线程B可见ø
        // 主线程启动子线程
        B.start();
        try {
            B.join(); //主线程会进入等待状态，一直到调用join()方法的线程执行结束为止。
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(var);
        // 子线程所有对共享变量的修改
        // 在主线程调用B.join()之后皆可见
        // 此例中，var==66
    }

    public static void main(String args[]) {
//        startRule();
        joinRule();
//        final int finalVal = 20;
    }


}
