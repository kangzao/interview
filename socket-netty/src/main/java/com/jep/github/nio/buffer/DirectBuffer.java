package com.jep.github.nio.buffer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/*
 * @author: enping.jep
 * @create: 2022-01-13 7:23 下午
 * 直接缓冲区
 */
public class DirectBuffer {

  public static void main(String args[]) throws IOException {
    //首先我们从磁盘上读取刚才我们写出的文件内容
    String infile = "//Users//Ian//book//test.txt";
    FileInputStream fin = new FileInputStream(infile);
    FileChannel fcin = fin.getChannel();
//把刚刚读取的内容写入到一个新的文件中
    String outfile = String.format("//Users//Ian//book//testcopy.txt");
    FileOutputStream fout = new FileOutputStream(outfile);
    FileChannel fcout = fout.getChannel();
// 使用 allocateDirect，而不是 allocate
    ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
    while (true) {
      buffer.clear();
      int r = fcin.read(buffer);
      if (r == -1) {
        break;
      }
      buffer.flip();
      fcout.write(buffer);
    }
  }
}
