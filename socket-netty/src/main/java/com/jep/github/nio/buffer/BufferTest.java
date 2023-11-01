package com.jep.github.nio.buffer;

import org.junit.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;

/**
 * @author enping.jep
 * @date 2023/10/31 15:04
 **/
public class BufferTest {

    @Test
    public void testConect1() throws IOException {
        RandomAccessFile aFile = new RandomAccessFile("//Users//Ian//book//test.txt", "rw");
        FileChannel inChannel = aFile.getChannel();
        //create buffer with capacity of 48 bytes
        ByteBuffer buf = ByteBuffer.allocate(48);
        int bytesRead = inChannel.read(buf); //read into buffer.
        while (bytesRead != -1) {
            buf.flip(); //make buffer ready for read
            while (buf.hasRemaining()) {
                System.out.print((char) buf.get()); // read 1 byte at a time
            }
            buf.clear(); //make buffer ready for writing
            bytesRead = inChannel.read(buf);
        }
        aFile.close();
    }

    @Test
    public void testConect2() throws IOException {
// 分配新的 int 缓冲区，参数为缓冲区容量
// 新缓冲区的当前位置将为零，其界限(限制位置)将为其容量。// 它将具有一个底层实现数组，其数组偏移量将为零。
        IntBuffer buffer = IntBuffer.allocate(8);
        for (int i = 0; i < buffer.capacity(); ++i) {
            int j = 2 * (i + 1);
// 将给定整数写入此缓冲区的当前位置，当前位置递增
            buffer.put(j);
        }
// 重设此缓冲区，将限制设置为当前位置，然后将当前位置设置为0
        buffer.flip();
// 查看在当前位置和限制位置之间是否有元素
        while (buffer.hasRemaining()) {
// 读取此缓冲区当前位置的整数，然后当前位置递增
            int j = buffer.get();
            System.out.print(j + " ");
        }
    }

    @Test
    public void testConect3() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(10);
// 缓冲区中的数据 0-9
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
