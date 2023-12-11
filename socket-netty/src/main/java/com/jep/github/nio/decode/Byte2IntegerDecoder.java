package com.jep.github.nio.decode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Byte2IntegerDecoder extends ByteToMessageDecoder {
    private final static Logger logger = LoggerFactory.getLogger(Byte2IntegerDecoder.class);

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //ByteToMessageDecoder的子类实现decode方法，从输入缓冲区中读取到整数，将一个个二进制数据解码成一个个整数，
        //之后不断循环解码，将解码后的整数添加到decode方法的List<Object>参数中；
        while (in.readableBytes() >= 4) {
            int i = in.readInt();
            logger.info("解码出一个整数:{}", i);
            out.add(i);
        }
        //decode方法处理完成后，ByteToMessageDecoder会继续向后传递，
        //将List<Object>结果中的数据一个一个传递到下一个入站处理器；
    }
}