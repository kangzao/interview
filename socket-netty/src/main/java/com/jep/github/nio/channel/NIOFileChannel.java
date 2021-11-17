package com.jep.github.nio.channel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/*
 *
 * @author: enping.jep
 * @create: 2021-11-16 11:18 上午
 */
public class NIOFileChannel {

  public static void main(String args[]) throws IOException {
    String str = "hello";
    FileOutputStream fileOutputStream = new FileOutputStream("//Users//Ian//book//test.txt");
    //通过fileOutputStream获取对应的fileChannel，这个fileChannel真实类型是FileChannelImpl
    FileChannel fileChannel = fileOutputStream.getChannel();
    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

    //将str放入到buffer中
    byteBuffer.put(str.getBytes());
    byteBuffer.flip();
    //将byteBuffer数据写入到fileChannel
    fileChannel.write(byteBuffer);
    fileOutputStream.close();
  }

}
