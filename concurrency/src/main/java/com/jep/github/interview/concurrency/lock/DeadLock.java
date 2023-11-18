package com.jep.github.interview.concurrency.lock;

import java.util.concurrent.locks.ReentrantLock;

public class DeadLock extends Thread {
    protected Object myDirect;
    static ReentrantLock south = new ReentrantLock();
    static ReentrantLock north = new ReentrantLock();

    public DeadLock(Object obj) {
        this.myDirect = obj;
        if (myDirect == south) {
            this.setName("south");
        }
        if (myDirect == north) {
            this.setName("north");
        }
    }

    @Override
    public void run() {
        if (myDirect == south) {
            try {
                north.lockInterruptibly();
                //占用north
                try {
                    Thread.sleep(500);
                    //等待north启动
                } catch (Exception e) {
                    e.printStackTrace();
                }
                south.lockInterruptibly();
                //占用south
                System.out.println("car to south has passed ");
            } catch (InterruptedException e1) {
                System.out.println("car to south is killed ");
            } finally {
                if (north.isHeldByCurrentThread())
                    north.unlock();
                if (south.isHeldByCurrentThread())
                    south.unlock();
            }
        }
        if (myDirect == north) {
            try {
                south.lockInterruptibly();
                //占用south
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                north.lockInterruptibly();
                //占用north
                System.out.println("car to north has passed ");
            } catch (InterruptedException e1) {
                System.out.println("car to north is killed ");
            } finally {
                if (north.isHeldByCurrentThread())
                    north.unlock();
                if (south.isHeldByCurrentThread())
                    south.unlock();
            }
        }
    }

    public static void main(String[] args) throws
            InterruptedException {
        DeadLock car2south = new DeadLock(south);
        //两个线程死锁
        DeadLock car2north = new DeadLock(north);
        car2south.start();
        car2north.start();
        Thread.sleep(1000);
    }
}