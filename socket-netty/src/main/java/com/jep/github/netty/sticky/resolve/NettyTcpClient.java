package com.jep.github.netty.sticky.resolve;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyTcpClient {

    private  int serverPort;


    public NettyTcpClient(int serverPort){
        this.serverPort = serverPort;
    }

    public void start() throws InterruptedException {
        NioEventLoopGroup worker = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(worker)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new NettyTcpClientChannelHandler());
                        //这里添加一个编码器，对发送的数据进行编码，因为编码器属于出站的Handler。
                        //而上面的业务逻辑Handler：NettyTcpClientChannelHandler属于入站Handler，所以添加顺序在这里暂时还没所谓。
                        pipeline.addLast(new MessageProtocolEncode());
                    }
                });

        ChannelFuture connect = bootstrap.connect("127.0.0.1", serverPort);
        connect.channel().closeFuture().sync();
    }

    public static void main(String[] args) throws InterruptedException {
        NettyTcpClient nettyTcpClient = new NettyTcpClient(8989);
        nettyTcpClient.start();
    }
}

