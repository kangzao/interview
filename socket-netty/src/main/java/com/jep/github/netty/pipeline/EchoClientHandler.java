package com.jep.github.netty.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {    //客户端连接服务器的时候调用
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端连接服务器。。。。");
        byte[] req = "QUERY TIME ORDER".getBytes();
        ByteBuf copiedBuffer = Unpooled.buffer(req.length);
        copiedBuffer.writeBytes(req);
        ctx.writeAndFlush(copiedBuffer);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf bytbuf) throws Exception {
        System.out.println("client get the server's data");
        byte[] resp = new byte[bytbuf.readableBytes()];
        bytbuf.readBytes(resp);
        String respContent = new String(resp, "UTF-8");
        System.out.println("返回的数据：" + respContent);
    }    //强制关闭服务器的连接也会造成异常：远程主机强迫关闭了一个现有的连接。

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println(cause.getLocalizedMessage());
        ctx.close();
    }

}