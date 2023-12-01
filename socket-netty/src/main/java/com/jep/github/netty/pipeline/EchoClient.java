package com.jep.github.netty.pipeline;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class EchoClient {

    public static void main(String[] args) throws InterruptedException {
        new EchoClient("localhost", 20000).start();
    }

    private final String host;
    private final int port;

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    private void start() throws InterruptedException {        //1.创建线程组
        EventLoopGroup group = new NioEventLoopGroup();
        try {            //2. 创建客户端启动对象，同样需要装配线程组，通道，绑定远程地址，请求处理器链。
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class).remoteAddress(host, port)
                    .handler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline().addLast(new EchoClientHandler());
                        }
                    });
            ChannelFuture future = bootstrap.connect().sync();         //3.开始请求连接
            future.channel().closeFuture().sync();//4.当请求操作结后，关闭通道。
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (group != null) {
                group.shutdownGracefully().sync();
            }
        }
    }

}