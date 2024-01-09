package com.jep.github.netty.sticky.resolve;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

//由原来的直接发送字符串，修改成现在使用协议对象来发送
public class NettyTcpClientChannelHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //发生这个事件代表通道已经顺利连接到远端，可以收发数据。我们就在这里发送数据、
        //分十次发送
        for (int i = 0; i < 10; i++) {
            byte[] content = "Hello I am John;".getBytes(CharsetUtil.UTF_8);
            //创建协议对象
            MessageProtocol message = new MessageProtocol(content);
            //发送
            ctx.channel().writeAndFlush(message);
        }
        super.channelActive(ctx);
    }
}

