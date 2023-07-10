package com.jep.github.interview.concurrency.cyclicBarrier;// Java program to demonstrate Execution on Cyclic Barrier

// Importing required classes

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

// Class 1
// Class implementing Runnable interface
class Computation1 implements Runnable {

    public static int product = 0;

    public void run() {
        product = 2 * 3;
        try {
            // thread1 awaits for other threads
            Tester.newBarrier.await();
        } catch (InterruptedException
                 | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}

// Class 2
// Implementing Runnable interface
class Computation2 implements Runnable {

    public static int sum = 0;

    public void run() {
        // check if newBarrier is broken or not
        System.out.println("Is the barrier broken? - "
                + Tester.newBarrier.isBroken());
        sum = 10 + 20;
        try {
            Tester.newBarrier.await(3000,
                    TimeUnit.MILLISECONDS);

            // number of parties waiting at the barrier
            System.out.println(
                    "Number of parties waiting at the barrier "
                            + "at this point = "
                            + Tester.newBarrier.getNumberWaiting());
        } catch (InterruptedException
                 | BrokenBarrierException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}

public class Tester implements Runnable {

    // create a static CyclicBarrier instance
    public static CyclicBarrier newBarrier
            = new CyclicBarrier(3);

    public static void main(String[] args) {
        // parent thread
        Tester test = new Tester();

        Thread t1 = new Thread(test);

        // Starting the thread using start() method
        t1.start();
    }

    // Method
    public void run() {
        // Print statement
        System.out.println(
                "Number of parties required to trip the barrier = "
                        + newBarrier.getParties());
        System.out.println(
                "Sum of product and sum = "
                        + (Computation1.product + Computation2.sum));

        // Creating object of class 1 objects
        // on which the child thread has to run
        Computation1 comp1 = new Computation1();
        Computation2 comp2 = new Computation2();

        // creation of child thread
        Thread t1 = new Thread(comp1);
        Thread t2 = new Thread(comp2);

        // Moving child thread to runnable state
        t1.start();
        t2.start();

        try {
            // parent thread awaits
            Tester.newBarrier.await();
        } catch (InterruptedException
                 | BrokenBarrierException e) {

            // Display exceptions along with line number
            // using printStackTrace() method
            e.printStackTrace();
        }

        // barrier breaks as the number of thread waiting
        // for the barrier at this point = 3
        System.out.println(
                "Sum of product and sum = "
                        + (Computation1.product + Computation2.sum));

        // Resetting the newBarrier
        newBarrier.reset();
        System.out.println("Barrier reset successful");
    }
}