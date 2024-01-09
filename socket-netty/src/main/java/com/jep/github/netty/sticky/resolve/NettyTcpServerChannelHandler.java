package com.jep.github.netty.sticky.resolve;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class NettyTcpServerChannelHandler extends ChannelInboundHandlerAdapter {

    private int count;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        MessageProtocol messageProtocol = (MessageProtocol) msg;
        System.out.println("数据是：" + new String(messageProtocol.getContent(), CharsetUtil.UTF_8) + ",长度是：" + messageProtocol.getLen());
        System.out.println("count = " + (++count));
        super.channelRead(ctx, msg);
    }
}
