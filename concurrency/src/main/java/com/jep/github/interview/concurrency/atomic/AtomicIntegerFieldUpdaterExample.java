package com.jep.github.interview.concurrency.atomic;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class AtomicIntegerFieldUpdaterExample {
    // 定义一个内部类
    static class Data {
        volatile int value;
    }

    // 创建 AtomicIntegerFieldUpdater 对象
    private static AtomicIntegerFieldUpdater<Data> updater =
            AtomicIntegerFieldUpdater.newUpdater(Data.class, "value");

    public static void main(String[] args) {
// 创建 Data 对象
        Data data = new Data();
        data.value = 0;
// 尝试原子更新字段的值
        boolean success = updater.compareAndSet(data, 0, 1);
        System.out.println("Value modified: " + success);
// 输出最终的字段值
        System.out.println("Final value: " + updater.get(data));
    }
}