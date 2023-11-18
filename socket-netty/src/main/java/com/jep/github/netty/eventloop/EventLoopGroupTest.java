package com.jep.github.netty.eventloop;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author enping.jep
 * @date 2023/11/14 15:33
 **/
public class EventLoopGroupTest {
    public static void main(String[] args) {
        EventLoopGroup group1 = new NioEventLoopGroup(3);
        EventLoopGroup group2 = new DefaultEventLoopGroup(2);

//      使用group.next()来获取下一个LoopGroup对象
        System.out.println(group1.next());
        System.out.println(group2.next());
//      使用execute()方法执行普通任务
        group1.next().execute(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + "   hello " + i);
            }
        });
//      使用 scheduleAtFixedRate()方法来处理定时任务
        group2.next().scheduleAtFixedRate(() -> {
            System.out.println(Thread.currentThread().getName() + "  hello2");
        }, 0, 1, TimeUnit.SECONDS);
        group1.shutdownGracefully();
        group2.shutdownGracefully();
    }

    @Test
    public void testWorkers() throws Exception {
        new ServerBootstrap()
                .group(new NioEventLoopGroup(1), new NioEventLoopGroup(2))
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) {
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) {
                                ByteBuf byteBuf = msg instanceof ByteBuf ? ((ByteBuf) msg) : null;
                                if (byteBuf != null) {
                                    byte[] buf = new byte[16];
                                    ByteBuf len = byteBuf.readBytes(buf, 0, byteBuf.readableBytes());
                                    System.out.println("read:" + new String(buf));
                                }
                            }
                        });
                    }
                }).bind(8181).sync();

        Channel channel = new Bootstrap()
                .group(new NioEventLoopGroup(1))
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        System.out.println("init...");
                        ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                    }
                })
                .channel(NioSocketChannel.class).connect("localhost", 8181)
                .sync()
                .channel();

        channel.writeAndFlush(ByteBufAllocator.DEFAULT.buffer().writeBytes("wangwu".getBytes()));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        channel.writeAndFlush(ByteBufAllocator.DEFAULT.buffer().writeBytes("wangwu".getBytes()));

    }
}
