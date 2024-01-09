package com.jep.github.netty.sticky.resolve;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

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
                        //这里添加一个解码器，顺序必须在业务处理的Handler前面。
                        //因为要对数据先解码了才能进行业务处理。
                        pipeline.addLast(new MessageProtocolDecoder());
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
