package com.jep.github.nio.buffer;

import java.nio.IntBuffer;

public class BasicBuffer {

    public static void main(String[] args) {

        //举例说明 Buffer 的使用(简单说明)
        //创建一个 Buffer，大小为 5，即可以存放 5 个 int
        IntBuffer intBuffer = IntBuffer.allocate(5);

        //向buffer存放数据
        //初始化过程中mark== -1  position == 0 limit == 5  capacity == 5
        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i * 2);//position会不断增大
        }
        //如何从 buffer 读取数据
        //将 buffer 转换，读写切换(!!!)  此时position会增大到5
        intBuffer.flip();
        intBuffer.position(1);//表示从第二个位置开始读取
        intBuffer.limit(3);//缓冲区的终点，极限位置，不能操作
        //此时pos == 0
        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get()); //position会增大到5
        }
    }
}