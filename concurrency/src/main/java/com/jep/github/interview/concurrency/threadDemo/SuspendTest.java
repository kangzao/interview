package com.jep.github.interview.concurrency.threadDemo;

public class SuspendTest {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(SuspendTest::sayHelloWorld);

        thread.start();
        synchronized (SuspendTest.class) {
            thread.resume();
        }
        System.out.printf("%s is over \n", Thread.currentThread().getName());

    }

    private static void sayHelloWorld() {
        Object monitor = SuspendTest.class;
        synchronized (monitor) {
            try {
                Thread.currentThread().suspend();
                monitor.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.printf("线程[%s] 恢复执行: <==============> Hello World!\n", Thread.currentThread().getName());
        }
    }

}
