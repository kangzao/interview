package com.jep.github.netty.unpack;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.CharsetUtil;

public class NettyTcpServer {


    private int port;

    public NettyTcpServer(int port) {
        this.port = port;
    }


    public void start() throws InterruptedException {

        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();

        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new NettyTcpServerChannelHandler());
                    }
                });

        ChannelFuture channelFuture = serverBootstrap.bind(port);
        channelFuture.channel().closeFuture().sync();
    }

    public static void main(String[] args) throws InterruptedException {
        NettyTcpServer nettyTcpServer = new NettyTcpServer(8989);
        nettyTcpServer.start();
    }
}


class NettyTcpServerChannelHandler extends ChannelInboundHandlerAdapter {

    private int count;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //每次发送的数据长度都小于102400，并且发送次数count>10次，所以发生了拆包。
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("长度是：" + byteBuf.readableBytes());
        System.out.println("count = " + (++count));
        super.channelRead(ctx, msg);
    }
}
