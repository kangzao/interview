package com.jep.github.nio.channel;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author enping.jep
 * @date 2023/11/21 13:58
 **/
public class ChannelTestServer {

    public static void main(String[] args) throws InterruptedException {
        Channel channel = new ServerBootstrap()
                .group(new NioEventLoopGroup())
                // 设置底层编程模型或者说底层通信模式，一旦设置中途不允许更改。所谓的底层编程模型，其实就是JDK的BIO，NIO模型（Netty摈弃了JDK的AIO编程模型），
                // 除此之外Netty还提供了自己编写的Epoll模型，当然日常工作中是用最多的还是NIO模型。
                .channel(NioServerSocketChannel.class)
                // childHandler方法主要作用是初始化和定义处理链来处理请求处理的细节
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new LoggingHandler());
                    }
                })
                // bind操作是一个异步方法，它会返回ChannelFuture
                .bind(8081)
                .channel();
        //isOpen:true,isRegistered:false,isActive:false
        System.out.println("isOpen:" + channel.isOpen() + ",isRegistered:" + channel.isRegistered() + ",isActive:" + channel.isActive());

        System.out.println("eventLoop():" + channel.eventLoop());
        System.out.println("pipeline():" + channel.pipeline());

        TimeUnit.SECONDS.sleep(5);
        System.out.println("=============================");
        //isOpen:true,isRegistered:true,isActive:true
        System.out.println("isOpen:" + channel.isOpen() + ",isRegistered:" + channel.isRegistered() + ",isActive:" + channel.isActive());
    }
}
