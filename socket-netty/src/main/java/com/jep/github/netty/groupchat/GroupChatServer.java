package com.jep.github.netty.groupchat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class GroupChatServer {

    private int port; //监听端口
    public GroupChatServer(int port) {
        this.port = port;
    }
    //编写run方法，处理客户端的请求
    public void run() throws Exception {
        //创建两个线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(); //8个NioEventLoop

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    //它用于设置服务器套接字的监听队列长度。当服务器接收到连接请求时，如果已经达到最大并发连接数，新的连接请求会被放入监听队列中，直到被服务器处理。
                    .option(ChannelOption.SO_BACKLOG, 128)
                    //用于设置 TCP 连接的保活（SO_KEEPALIVE）属性。当启用此选项时，如果连接在一定时间内没有任何数据传输，操作系统将自动发送一个探测包以检查连接是否仍然有效。
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //获取到pipeline
                            ChannelPipeline pipeline = ch.pipeline();
                            //向pipeline加入解码器
                            pipeline.addLast("decoder", new StringDecoder());
                            //向pipeline加入编码器
                            pipeline.addLast("encoder", new StringEncoder());
                            //加入自己的业务处理handler
                            pipeline.addLast(new GroupChatServerHandler());
                        }
                    });
            System.out.println("netty 服务器启动");
            // bind(int port) 方法配置Netty服务器以监听指定端口上的连接请求
            // bind 方法有一个同步版本（sync()）和一个异步版本（await()）。sync() 会阻塞当前线程直到操作完成，而 await() 允许程序继续执行其他任务直到操作完成。
            ChannelFuture channelFuture = b.bind(port).sync();
            //Channel 接口的 closeFuture 方法返回一个 ChannelFuture 实例，该实例代表 Channel 关闭操作的异步结果
            //调用sync方法时，当前线程将被阻塞，直到 ChannelFuture 表示的操作完成。如果操作成功完成，sync 方法将返回 ChannelFuture 实例本身；如果操作失败，它将抛出一个 ExecutionException。
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        new GroupChatServer(7000).run();
    }
}