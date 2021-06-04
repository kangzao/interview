package com.jep.github.interview.lock.cas;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author enping
 * @date 2021/4/18 下午1:11
 **/
public class UnsafeDemo {
    volatile int value = 0;
    static Unsafe unsafe;//直接操作内存、修改对象、数组内存
    private static long valueOffset;

    Field field;

    {
        try {
            field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
            //获取value属性的偏移量
            valueOffset = unsafe.objectFieldOffset(UnsafeDemo.class.getDeclaredField("value"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void add() {
        int current;
        do {
            current = unsafe.getIntVolatile(this, valueOffset);
        } while (!unsafe.compareAndSwapInt(this, valueOffset, current, current + 1));

    }


    public static void main(String[] args) throws InterruptedException {
        UnsafeDemo ld = new UnsafeDemo();
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    ld.add();
                }
            }).start();
        }
        Thread.sleep(2000L);
        System.out.println(ld.value);
    }
}
