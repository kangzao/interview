package com.jep.github.interview.jvm.directmemory;

import java.nio.ByteBuffer;
import java.util.ArrayList;

public class BufferTest2 {
    private static final int BUFFER = 1024 * 1024 * 20; // 20MB

    public static void main(String[] args) {
        ArrayList<ByteBuffer> list = new ArrayList<>();

        int count = 0;
        try {
            while (true) {
                ByteBuffer byteBuffer = ByteBuffer.allocateDirect(BUFFER);
                list.add(byteBuffer);
                count++;
            }
        } finally {
            System.out.println(count);
        }
    }
}