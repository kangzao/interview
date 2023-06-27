package com.jep.github.interview.concurrency.completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class AccountTransactionExample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
// 模拟查询账户余额的异步操作
        CompletableFuture<Double> balanceFuture = CompletableFuture.supplyAsync(() -> {
// 模拟耗时操作
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1000.0;
        });
// 模拟查询最近交易记录的异步操作
        CompletableFuture<Transaction> transactionFuture = CompletableFuture.supplyAsync(() -> {
// 模拟耗时操作
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return new Transaction(1, "2023-05-25", 500.0);
        });
// 使用 thenCombine 方法组合异步操作的结果
        CompletableFuture<AccountInfo> combinedFuture = balanceFuture.thenCombine(transactionFuture, (balance, transaction) -> {
            return new AccountInfo(balance, transaction);
        });
// 输出账户信息
        System.out.println(combinedFuture.get()); // 输出 "AccountInfo{balance=1000.0, transaction=Transaction{id=1, date='2023-05-25', amount=500.0}}"
    }
}

class AccountInfo {
    private double balance;
    private Transaction transaction;

    public AccountInfo(double balance, Transaction transaction) {
        this.balance = balance;
        this.transaction = transaction;
    }

    public double getBalance() {
        return balance;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    @Override
    public String toString() {
        return "AccountInfo{" +
                "balance=" + balance +
                ", transaction=" + transaction +
                '}';
    }
}

class Transaction {
    private int id;
    private String date;
    private double amount;

    public Transaction(int id, String date, double amount) {
        this.id = id;
        this.date = date;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", amount=" + amount +
                '}';
    }
}