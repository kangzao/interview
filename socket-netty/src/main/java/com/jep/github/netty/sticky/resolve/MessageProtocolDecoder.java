package com.jep.github.netty.sticky.resolve;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class MessageProtocolDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // 记录初始的读偏移量，用于拆包情况下的偏移量回滚
        int beginReadIndex = in.readerIndex();

        // 首先读数据的长度出来先
        int len = in.readInt();

        // 如果可读的字节数小于数据长度，说明数据不完整，需要处理拆包问题
        if (in.readableBytes() < len) {
            // 重置读偏移量，等待下次读取
            in.readerIndex(beginReadIndex);
            // 直接返回，等待下次读取完成
            return;
        }

        // 能到这里，说明数据包是完整的

        // 读取数据
        byte[] data = new byte[len];
        in.readBytes(data);

        // 创建MessageProtocol对象封装数据
        MessageProtocol messageProtocol = new MessageProtocol(data);

        // 将封装好的MessageProtocol对象添加到输出列表，供后续处理
        out.add(messageProtocol);
    }

}
