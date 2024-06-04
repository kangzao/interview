package com.jep.github.netty.pipeline;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.nio.charset.StandardCharsets;

public class NettyClient {

    public static void main(String[] args) throws Exception {
        // 启动客户端
        startClient();
    }

    public static void startClient() throws InterruptedException {
        Bootstrap clientBootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();

        clientBootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ChannelPipeline pipeline = ch.pipeline();
                        // client发送消息的顺序为CustomOutboundHandler1 -> CustomOutboundHandler2  只会执行OutboundHandler
                        pipeline.addLast(new CustomOutboundHandler2());
                        pipeline.addLast(new CustomOutboundHandler1());

                        //client接收到server返回的消息，执行顺序为  CustomInboundHandler2 -> CustomInboundHandler1
                        pipeline.addLast(new CustomInboundHandler2());
                        pipeline.addLast(new CustomInboundHandler1());
                        pipeline.addLast(new SimpleChannelInboundHandler<ByteBuf>() {
                            @Override
                            public void channelActive(ChannelHandlerContext ctx) {
                                ByteBuf firstMessage;
                                byte[] req = "你好服务器".getBytes();
                                firstMessage = Unpooled.buffer(req.length);
                                firstMessage.writeBytes(req);
                                System.out.println(this.getClass() + "------------ 开始发送消息 ------------");
                                //在handler中调用ctx.writeAndFlush()方法后，就会将数据交给ChannelOutboundHandler进行出站处理
                                ctx.writeAndFlush(firstMessage);//ctx.writeAndFlush只会从当前的handler位置开始，往前找outbound执行
                            }

                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) {
                                //buf.readableBytes()获取缓冲区可读的字节数
                                byte[] req = new byte[msg.readableBytes()];
                                // 将缓冲区的字节数组复制到新的byte数组中
                                msg.readBytes(req);
                                String body = new String(req, StandardCharsets.UTF_8);

                                System.out.println("Client received: " + body);
                            }

                        });
                    }
                });

        ChannelFuture future = clientBootstrap.connect("localhost", 8188).sync();
        future.channel().closeFuture().sync();
    }
}
