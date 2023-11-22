package com.jep.github.nio.channel;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author enping.jep
 * @date 2023/11/21 14:01
 **/
public class ChannelTestClient {

    public static void main(String[] args) throws InterruptedException {
        Channel channel = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new LoggingHandler());
                    }
                })
                .connect("127.0.0.1", 8081)
                .channel();

        // isOpen:true,isRegistered:false,isActive:false
        System.out.println("isOpen:" + channel.isOpen() + ",isRegistered:" + channel.isRegistered() + ",isActive:" + channel.isActive());

        System.out.println("eventLoop():" + channel.eventLoop());
        System.out.println("pipeline():" + channel.pipeline());

        TimeUnit.SECONDS.sleep(5);
        System.out.println("=============================");
        // isOpen:true,isRegistered:true,isActive:true
        System.out.println("isOpen:" + channel.isOpen() + ",isRegistered:" + channel.isRegistered() + ",isActive:" + channel.isActive());
    }
}
