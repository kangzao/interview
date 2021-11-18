package com.jep.github.nio.channel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author enping
 * @date 2021/11/16 10:09 下午
 * 读取文件
 **/
public class NIOFileChannel1 {
    public static void main(String[] args) throws IOException {
        //创建文件的输入流
        File file = new File("//Users//enping//Documents//book//test.txt");
        FileInputStream inputStream = new FileInputStream(file);
        //通过FileInputStream获取fileChannel
        FileChannel fileChannel = inputStream.getChannel();

        //创建缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());

        //将通道数据读入buffer
        fileChannel.read(byteBuffer);

        System.out.println(new String(byteBuffer.array()));

    }


}
