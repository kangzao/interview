package com.jep.github.netty.unpack;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

import java.util.Arrays;

public class NettyTcpClient {

    private int serverPort;


    public NettyTcpClient(int serverPort) {
        this.serverPort = serverPort;
    }

    public void start() throws InterruptedException {
        NioEventLoopGroup worker = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(worker)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new NettyTcpClientChannelHandler());
                    }
                });

        ChannelFuture connect = bootstrap.connect("127.0.0.1", serverPort);
        connect.channel().closeFuture().sync();
    }

    public static void main(String[] args) throws InterruptedException {
        NettyTcpClient nettyTcpClient = new NettyTcpClient(8989);
        nettyTcpClient.start();
    }
}


class NettyTcpClientChannelHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //发生这个事件代表通道已经顺利连接到远端，可以收发数据。我们在这里发送数据、
        //分十次发送
        //一次发送102400字节数据 发送数据比mss大
        byte[] bytes = new byte[102400];
        for (int i = 0; i < 10; i++) {
            ctx.channel().writeAndFlush(Unpooled.copiedBuffer(bytes));
        }
        ctx.fireChannelActive();
    }

}
