package com.jep.github.interview.concurrency.fork;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class AverageCalculator extends RecursiveTask<Double> { // 定义一个继承自RecursiveTask的类AverageCalculator，用于计算整数数组平均值
    private final int[] array; // 定义整数数组
    private final int start; // 定义数组起始位置
    private final int end; // 定义数组结束位置

    public AverageCalculator(int[] array, int start, int end) { // 构造函数，初始化数组和起、结束位置
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Double compute() { // 重写compute()方法，用于计算平均值
        if (end - start <= 100) { // 如果数组元素数量小于等于100，则直接计算平均值并返回
            double sum = 0;
            for (int i = start; i < end; i++) {
                sum += array[i];
            }
            return sum / (end - start);
        } else { // 如果数组元素数量大于100，则将数组分成两个部分，分别创建两个子任务，并使用fork()方法提交到ForkJoinPool中进行计算
            int mid = (start + end) / 2;
            AverageCalculator left = new AverageCalculator(array, start, mid);
            AverageCalculator right = new AverageCalculator(array, mid, end);
            left.fork(); // 创建子任务
            double rightResult = right.compute();
// 等待子任务完成
            double leftResult = left.join();
// 等待子任务的计算结果，并将两个子任务的平均值相加再除以2，得到整个数组的平均值
            return (leftResult + rightResult) / 2;
        }
    }

    public static void main(String[] args) { // 主函数
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}; // 定义整数数组
        ForkJoinPool pool = new ForkJoinPool(); // 创建ForkJoinPool对象
        double result = pool.invoke(new AverageCalculator(array, 0, array.length)); // 使用invoke()方法提交一个AverageCalculator任务进行计算
        System.out.println("The average is " + result); // 输出计算结果
    }
}