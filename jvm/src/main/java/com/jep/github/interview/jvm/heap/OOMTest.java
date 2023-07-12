package com.jep.github.interview.jvm.heap;

import java.util.ArrayList;
import java.util.List;

public class OOMTest {
    public static void main(String[] args) {
        List<byte[]> list = new ArrayList<>();
        while (true) {
            list.add(new byte[1024 * 1024]); // 每次增加一个1M大小的数组对象
        }
    }
}