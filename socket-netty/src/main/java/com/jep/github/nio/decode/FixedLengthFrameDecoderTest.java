package com.jep.github.nio.decode;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import org.junit.Test;

public class FixedLengthFrameDecoderTest {
    @Test
    public void testFrameDecoded() {
        //创建一个ByteBuf，并存储9字节
        ByteBuf buf = Unpooled.buffer();
        for (int i = 0; i < 9; i++) {
            buf.writeByte(i);
        }
        //返回ByteBuf可读字节的一部分。 修改返回的ByteBuf或当前ByteBuf会影响彼此的内容，
        //同时它们维护单独的索引和标记,此方法不会修改当前ByteBuf的readerIndex或writerIndex
        ByteBuf input = buf.duplicate();
        EmbeddedChannel channel = new EmbeddedChannel(new FixedLengthFrameDecoder(3));
        //将数据写入EmbeddedChannel   Netty 的 ByteBuf 是通过引用计数的方式管理的，如果一个 ByteBuf 没有地方被引用到，需要回收底层内存。
        //默认情况下，当创建完一个 ByteBuf，它的引用为1，然后每次调用 retain() 方法， 它的引用就加一
        System.out.println(channel.writeInbound(input.retain()));//true
        //标记Channel为已完成状态
        System.out.println(channel.finish());//true

        //读取所生成的消息，并且验证是否有3帧，其中每帧都为3字节
        ByteBuf read = channel.readInbound();
        System.out.println(buf.readSlice(3).equals(read));//true

        read = channel.readInbound();
        System.out.println(buf.readSlice(3).equals(read));//true

        //ByteBuf的release()方法使引用计数减少1。只有当执行以后引用计数减少到0，该函数才返回true。当ByteBuf的引用计数减少到0时，ByteBuf会被释放。
        //当ByteBuf的引用计数是0时，再执行release()方法会抛出IllegalReferenceCountException异常。

        read.release();

        read = channel.readInbound();
        System.out.println(buf.readSlice(3).equals(read));//true
        read.release();

        System.out.println(channel.readInbound() == null);//true
        buf.release();
    }

    @Test
    public void testFramesDescode2() {
        ByteBuf buf = Unpooled.buffer();
        for (int i = 0; i < 9; i++) {
            buf.writeByte(i);
        }
        ByteBuf input = buf.duplicate();
        EmbeddedChannel channel = new EmbeddedChannel(new FixedLengthFrameDecoder(3));
        //返回false，因为没有一个完整的可供读取的帧
        System.out.println(channel.writeInbound(input.readBytes(2)));//false
        System.out.println(channel.writeInbound(input.readBytes(7)));//true

        System.out.println(channel.finish());//true
        ByteBuf read = channel.readInbound();
        System.out.println(buf.readSlice(3) == read);//false
        read.release();

        read = channel.readInbound();
        System.out.println(buf.readSlice(3) == read);//false
        read.release();

        read = channel.readInbound();
        System.out.println(buf.readSlice(3) == read);//false
        read.release();

        System.out.println(channel.readInbound() == null);//true
        buf.release();
    }

}
