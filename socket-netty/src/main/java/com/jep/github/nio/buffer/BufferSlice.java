package com.jep.github.nio.buffer;

import java.nio.ByteBuffer;


/**
 * 在该示例中，分配了一个容量大小为 10 的缓冲区，并在其中放入了数据 0-9，
 * 而在该缓冲区基础之上又创建了一个子缓冲区，并 改变子缓冲区中的内容，从最后输出的结果来看，
 * 只有子缓冲区“可见的”那部分数据发生了变化，并且说明子缓冲区与原缓冲区是 数据共享的
 *
 */



public class BufferSlice {
    static public void main(String args[]) throws Exception {
        ByteBuffer buffer = ByteBuffer.allocate(10);

        // 缓冲区中的数据0-9  
        for (int i = 0; i < buffer.capacity(); ++i) {
            buffer.put((byte) i);
        }

        // 创建子缓冲区  
        buffer.position(3);
        buffer.limit(7);
        ByteBuffer slice = buffer.slice();

        // 改变子缓冲区的内容  
        for (int i = 0; i < slice.capacity(); ++i) {
            byte b = slice.get(i);
            b *= 10;
            slice.put(i, b);
        }

        buffer.position(0);
        buffer.limit(buffer.capacity());

        while (buffer.remaining() > 0) {
            System.out.println(buffer.get());
        }
    }
}