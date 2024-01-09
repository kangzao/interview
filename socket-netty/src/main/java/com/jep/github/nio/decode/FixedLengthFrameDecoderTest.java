package com.jep.github.nio.decode;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import org.junit.Test;

public class FixedLengthFrameDecoderTest {
    @Test
    public void testFrameDecoded() {

        String content = "1234567890";
        //创建一个ByteBuf，并存储9字节
        ByteBuf buf = Unpooled.buffer(content.length());
        buf.writeBytes(content.getBytes());
        //返回ByteBuf可读字节的一部分。 修改返回的ByteBuf或当前ByteBuf会影响彼此的内容，
        //同时它们维护单独的索引和标记,此方法不会修改当前ByteBuf的readerIndex或writerIndex
        ByteBuf input = buf.duplicate();
        EmbeddedChannel channel = new EmbeddedChannel(new FixedLengthFrameDecoder(3));

        channel.finish();
        System.out.println(channel.readInbound().toString());
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
