package com.jep.github.interview.concurrency.cyclicBarrier;

import com.jep.github.interview.concurrency.lock.aqs.source.CyclicBarrier;

import java.util.concurrent.BrokenBarrierException;

public class CarRace {
    public static void main(String[] args) {
        int numCars = 3;
        int numRounds = 2;
        CyclicBarrier barrier = new CyclicBarrier(numCars,
                () -> System.out.println("所有赛车已准备好，开始新一圈比赛！"));
        for (int i = 0; i < numCars; i++) {
            new Thread(new Car(barrier, i, numRounds)).start();
        }
    }
}

class Car implements Runnable {
    private CyclicBarrier barrier;
    private int carNumber;
    private int numRounds;

    public Car(CyclicBarrier barrier, int carNumber, int numRounds) {
        this.barrier = barrier;
        this.carNumber = carNumber;
        this.numRounds = numRounds;
    }

    @Override
    public void run() {
        for (int i = 0; i < numRounds; i++) {
            try {
                System.out.println("赛车" + carNumber + "已准备好第" + (i + 1) + "圈比赛");
                barrier.await(); // 等待其他赛车准备好
                System.out.println("赛车" + carNumber + "正在进行第" + (i + 1) + "圈比赛");
                Thread.sleep((long) (Math.random() * 1000)); // 模拟比赛过程
                System.out.println("赛车" + carNumber + "已完成第" + (i + 1) + "圈比赛");
                barrier.await(); // 等待其他赛车完成比赛
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}