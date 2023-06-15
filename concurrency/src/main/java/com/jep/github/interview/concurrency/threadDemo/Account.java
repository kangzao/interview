package com.jep.github.interview.concurrency.threadDemo;

public class Account {
    private double balance;//余额
    private int points;//积分
    private final Object balanceLock = new Object();
    private final Object pointsLock = new Object();


    public void deposit(double amount) {
        synchronized (balanceLock) {
            balance += amount;
        }
    }

    public void withdraw(double amount) {
        synchronized (balanceLock) {
            balance -= amount;
        }
    }

    public void addPoints(int pointsToAdd) {
        synchronized (pointsLock) {
            points += pointsToAdd;
        }
    }

    public void usePoints(int pointsToUse) {
        synchronized (pointsLock) {
            points -= pointsToUse;
        }
    }
}
