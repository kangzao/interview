package com.jep.github.netty.bootstrap;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

import java.net.InetSocketAddress;

public class HttpServer {
    public void start(int port) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).localAddress(new InetSocketAddress(port)).childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) {
                    ch.pipeline().addLast("codec", new HttpServerCodec())                  // HTTP 编解码
                            .addLast("compressor", new HttpContentCompressor())       // HttpContent 压缩
                            .addLast("aggregator", new HttpObjectAggregator(65536))   // HTTP 消息聚合
                            .addLast("handler", new HttpServerHandler());             // 自定义业务逻辑处理器
                }
            }).childOption(ChannelOption.SO_KEEPALIVE, true);
            //ChannelFuture提供操作完成时一种异步通知的方式。一般在Socket编程中，等待响应结果都是同步阻塞的，而Netty则不会造成阻塞，因为ChannelFuture是采取类似观察者模式的形式进行获取结果。
            ChannelFuture f = b.bind().sync();
            System.out.println("Http Server started， Listening on " + port);
            /* 通过sync方法同步等待通道关闭处理完毕，这里会阻塞等待通道关闭完成，内部调用的是Object的wait()方法。
             * 它会让线程进入 wait 状态，这样服务端可以一直处于运行状态，如果没有这行代码，bind 操作之后就会进入 finally 代码块，整个服务端就退出结束了。**/
            f.channel().closeFuture().sync();

        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        new HttpServer().start(8088);
    }
}