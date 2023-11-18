package com.jep.github.nio.channel;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelDemo {
    public static void main(String[] args) throws IOException {

//        在使用 FileChannel 之前，必须先打开它。但是，我们无法直接打开一个FileChannel，
//        需要通过使用一个 InputStream、OutputStream或RandomAccessFile 来获取一个 FileChannel 实例
        RandomAccessFile aFile = new
                RandomAccessFile("//Users//Ian//book//test.txt", "rw");
        FileChannel inChannel = aFile.getChannel();
//        调用多个 read()方法之一从 FileChannel 中读取数据。首先，分配一个 Buffer。从 FileChannel 中读取的数据将被读到Buffer 中。
//        然后，调用 FileChannel.read()方法。该方法将数据从 FileChannel 读取到Buffer 中。
//        read()方法返回的 int 值表示了有多少字节被读到了 Buffer 中。如果返回-1，表示到了文件末尾。
        ByteBuffer buf = ByteBuffer.allocate(48);
        int bytesRead = inChannel.read(buf);
        while (bytesRead != -1) {
            System.out.println("读取： " + bytesRead);
//            调用 buffer.flip() 反转读写模式
            buf.flip();
            while (buf.hasRemaining()) {
                //打印每一行的数据
                System.out.print((char) buf.get());
            }
            buf.clear();
            bytesRead = inChannel.read(buf);
        }
        aFile.close();
        System.out.println("操作结束");
    }
}