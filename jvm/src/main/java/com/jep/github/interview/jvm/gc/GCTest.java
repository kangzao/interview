package com.jep.github.interview.jvm.gc;

import java.util.ArrayList;
import java.util.List;

public class GCTest {
    public static void main(String[] args) throws InterruptedException {
        int i = 0;
        try {
            List<String> list = new ArrayList<>();
            String a = "test";
            while (true) {
                list.add(a);
                a = a + a;
                i++;
            }

        } catch (Throwable t) {
            t.printStackTrace();
            System.out.println("遍历次数为：" + i);
        }
    }
}