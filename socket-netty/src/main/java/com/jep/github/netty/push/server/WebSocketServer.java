package com.jep.github.netty.push.server;

import com.jep.github.netty.push.test.TestCenter;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public final class WebSocketServer {

    static int PORT = 9000;

    public static void main(String[] args) throws Exception {

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_REUSEADDR, true)
                    .childHandler(new WebSocketServerInitializer())
                    .childOption(ChannelOption.SO_REUSEADDR, true);
            for (int i = 0; i < 100; i++) {
                b.bind(++PORT).addListener(new ChannelFutureListener() {
                    public void operationComplete(ChannelFuture future) throws Exception {
                        if ("true".equals(System.getProperty("netease.debug")))
                            System.out.println("端口绑定完成：" + future.channel().localAddress());
                    }
                });
            }
            // 端口绑定完成，启动消息随机推送(测试)
            TestCenter.startTest();

            System.in.read();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}