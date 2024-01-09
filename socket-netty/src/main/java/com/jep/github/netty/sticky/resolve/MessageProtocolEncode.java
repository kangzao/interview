package com.jep.github.netty.sticky.resolve;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MessageProtocolEncode extends MessageToByteEncoder<MessageProtocol> {


    @Override
    protected void encode(ChannelHandlerContext ctx, MessageProtocol msg, ByteBuf out) throws Exception {
        //对协议的编码时，把数据的前4个字节固定为数据的长度，跟着就是数据的内容
        int len = msg.getLen();
        //先写数据的长度
        out.writeInt(len);
        //再写数据的内容
        out.writeBytes(msg.getContent());
    }
}

