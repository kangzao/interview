package com.jep.github.interview.concurrency.cyclicBarrier;


import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CarRace {
    /**
     * 主程序入口
     * 模拟赛车比赛，每辆赛车在所有比赛回合中运行
     * 使用CyclicBarrier来同步赛车的起始点
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        // 定义参与比赛的赛车数量
        int numCars = 3;
        // 定义每个赛车的比赛回合数
        int numRounds = 2;

        // 初始化CyclicBarrier，用于同步赛车的起始点
        // 当所有赛车都到达屏障时，执行一次操作：打印开始新一圈比赛的信息
        CyclicBarrier barrier = new CyclicBarrier(numCars,
                () -> System.out.println("所有赛车已准备好，开始新一圈比赛！"));

        // 创建并启动每个赛车线程
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
                barrier.await(); // 等待其他赛车准备好  第一圈之前在跑道上在同一起跑线准备好了
                System.out.println("赛车" + carNumber + "正在进行第" + (i + 1) + "圈比赛");
                Thread.sleep((long) (Math.random() * 1000)); // 模拟比赛过程   //在赛道上跑完了
                System.out.println("赛车" + carNumber + "已完成第" + (i + 1) + "圈比赛");
                barrier.await(); // 等待其他赛车完成比赛
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}