package com.jep.github.netty.sticky.resolve;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class MessageProtocolDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //记录初始的读偏移量，用于拆包情况下的偏移量回滚
        int beginReadIndex = in.readerIndex();


        //首先读数据的长度出来先

        int len = in.readInt();

        if (in.readableBytes() < len){
            //这种情况下就是发生了拆包问题，因读取的数据长度没有len长，这个包还有数据没有到达。
            //重置读偏移量,等待下次重新读写
            in.readerIndex(beginReadIndex);
            //直接返回，等待下次
            return;
        }



        //能到这里，就证明包是完整的

        //读数据
        byte[] data = new byte[len];
        in.readBytes(data);



        MessageProtocol messageProtocol = new MessageProtocol(data);

        //写出给下一个Handler处理。
        out.add(messageProtocol);
    }
}
