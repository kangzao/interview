package com.jep.github.interview.jvm.string;

/**
 * 使用intern()测试执行效率：空间使用上
 * <p>
 * 结论：对于程序中大量存在存在的字符串，尤其其中存在很多重复字符串时，使用intern()可以节省内存空间。
 */
public class _71_StringIntern2 {

    static final int MAX_COUNT = 1000 * 10000;
    static final String[] arr = new String[MAX_COUNT];

    public static void main(String[] args) {
        Integer[] data = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        long start = System.currentTimeMillis();
        for (int i = 0; i < MAX_COUNT; i++) {
            // 不使用intern，对比
//            arr[i] = new String(String.valueOf(data[i % data.length])); // 花费的时间为：7314
            // 使用intern，对比
            arr[i] = new String(String.valueOf(data[i % data.length])).intern(); // 花费的时间为：4724
        }
        long end = System.currentTimeMillis();
        System.out.println("花费的时间为：" + (end - start));

        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.gc();
    }
}