package com.jep.github.interview.concurrency.fork;

import java.util.Arrays;
import java.util.concurrent.*;

/**
 * 让数组中的每一个元素都加1
 */
public class ArrayIncrementor extends RecursiveAction { // <1>
    int[] data; // <2>
    int hi, lo;
    static int THRESHOLD = 5; //5个以内直接加


    public ArrayIncrementor(int[] data, int lo, int hi) { // <3>
        this.data = data;
        this.lo = lo;
        this.hi = hi;
    }

    @Override
    protected void compute() {
        if (hi - lo <= THRESHOLD) {
            System.out.printf("compute: %d - %d %n", lo, hi);
            for (int i = lo; i < hi; i++) { // <1> 5个数字一组进行拆分
                data[i]++;
            }
        } else {
            int mid = (hi - lo) / 2 + lo; // <2>计算中间值
            System.out.printf("fork: %d - %d, %d - %d %n", lo, mid, mid, hi);
            invokeAll(new ArrayIncrementor(data, lo, mid),// 0 - 10
                    new ArrayIncrementor(data, mid, hi)); //  10 - 20 <3> 将一个ForkJoinTask提交到工作线程池中执行，等待所有任务完成后返回。
            System.out.printf("join: %d - %d %n", lo, hi);
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int[] input = new int[]{5, 2, 6, 1, 4, 5, 6, 9, 8, 5, 5, 2, 6, 1, 4, 5, 6, 9, 8, 5};
        ForkJoinPool fjp = new ForkJoinPool();
        fjp.invoke(new ArrayIncrementor(input, 0, input.length)); // <1> 提交forkjointask
        fjp.shutdown();
        System.out.printf("result: %s", Arrays.toString(input));
    }
}