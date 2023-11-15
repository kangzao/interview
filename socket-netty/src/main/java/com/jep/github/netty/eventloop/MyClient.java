package com.jep.github.netty.eventloop;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;

/**
 * @author enping.jep
 * @date 2023/11/14 17:20
 **/
public class MyClient {

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            connect(i);
        }
    }

    public static void connect(int i) throws InterruptedException {

        new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel channel) throws Exception {
                        channel.pipeline().addLast(new StringEncoder());
                    }
                })
                .connect(new InetSocketAddress("localhost", 8181))
                .sync()
                .channel()
                .writeAndFlush("hello client" + "  " + i);
    }
}
