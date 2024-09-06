package com.jep.github.netty.pipeline;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.nio.charset.StandardCharsets;

public class NettyServer {
    public static void main(String[] args) throws InterruptedException {
        // 启动服务器
        startServer();

    }

    private static void startServer() throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap();

        b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)//指定 channel 类型为 NioServerSocketChannel
                .option(ChannelOption.SO_BACKLOG, 1024) //用于设置服务器端TCP连接的最大排队连接数
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        // 将多个ChannelHandler 添加到一个ChannelPipeline 中,每个Channel中有且仅有一个ChannelPipeline
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new CustomInboundHandler1());//在ChannelPipeline的末尾添加ChannelHandler
                        pipeline.addLast(new CustomInboundHandler2());

                        pipeline.addLast(new SimpleChannelInboundHandler<ByteBuf>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) {
                                //buf.readableBytes()获取缓冲区可读的字节数
                                byte[] req = new byte[msg.readableBytes()];
                                // 将缓冲区的字节数组复制到新的byte数组中
                                msg.readBytes(req);
                                String body = new String(req, StandardCharsets.UTF_8);

                                System.out.println("Server received: " + body);
                                ByteBuf firstMessage;
                                byte[] req1 = "你好客户端".getBytes();
                                //创建缓冲区
                                firstMessage = Unpooled.buffer(req1.length);
                                firstMessage.writeBytes(req1);
                                System.out.println("开始给客户端发送消息");
                                ctx.writeAndFlush(firstMessage);
                            }

                            //Channel已经被注册到一个EventLoop上
                            @Override
                            public void channelRegistered(ChannelHandlerContext ctx) {
                                System.out.println("连接上来了");
                                ctx.fireChannelRegistered();
                            }
                        });
                        pipeline.addLast(new CustomOutboundHandler1());
                        pipeline.addLast(new CustomOutboundHandler2());
                    }
                });

        ChannelFuture future = b.bind(8188).sync();
        future.channel().closeFuture().sync();
    }
}

