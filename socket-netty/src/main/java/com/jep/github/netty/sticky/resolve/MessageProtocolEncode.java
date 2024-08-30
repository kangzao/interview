package com.jep.github.netty.sticky.resolve;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MessageProtocolEncode extends MessageToByteEncoder<MessageProtocol> {


/**
 * 编码消息处理方法
 * 该方法主要用于将消息协议对象（MessageProtocol）编码为ByteBuf对象，以便在网络中传输
 * 主要工作包括将消息长度和内容写入ByteBuf中
 *
 * @param ctx 上下文对象，包含处理此操作所需的所有信息
 * @param msg 要编码的消息对象
 * @param out 输出ByteBuf，用于存储编码后的数据
 * @throws Exception 异常
 */
@Override
protected void encode(ChannelHandlerContext ctx, MessageProtocol msg, ByteBuf out) throws Exception {
    //获取消息长度：通过msg.getLen()方法获取消息内容的长度，并存储在len变量中。
    int len = msg.getLen();
    //写入消息长度：使用out.writeInt(len)方法，将消息长度写入输出字节流的前4个字节。这4个字节用于表示后续数据的长度。
    out.writeInt(len);
    //写入消息的内容：使用out.writeBytes(msg.getContent())方法，将消息内容写入输出字节流。这里的内容是实际要传输的数据。
    out.writeBytes(msg.getContent());
}
}

