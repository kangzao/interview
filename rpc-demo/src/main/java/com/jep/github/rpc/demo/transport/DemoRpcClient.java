package com.jep.github.rpc.demo.transport;

import com.jep.github.rpc.demo.Constants;
import com.jep.github.rpc.demo.codec.DemoRpcDecoder;
import com.jep.github.rpc.demo.codec.DemoRpcEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.Closeable;

/**
 * 客户端
 *
 * @author enping.jep
 * @date 2024/07/29 11:45
 **/
public class DemoRpcClient implements Closeable {

    protected Bootstrap clientBootstrap;
    protected EventLoopGroup group;
    private String host;
    private int port;

    public DemoRpcClient(String host, int port) {
        this.host = host;
        this.port = port;
        // 创建并配置客户端Bootstrap
        clientBootstrap = new Bootstrap();
        group = NettyEventLoopFactory.eventLoopGroup(Constants.DEFAULT_IO_THREADS, "NettyClientWorker");
        clientBootstrap.group(group)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .channel(NioSocketChannel.class) // 创建的Channel类型
                // 指定ChannelHandler的顺序
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast("demo-rpc-encoder", new DemoRpcEncoder());
                        ch.pipeline().addLast("demo-rpc-decoder", new DemoRpcDecoder());
                        ch.pipeline().addLast("client-handler", new DemoRpcClientHandler());
                    }
                });
    }


    public ChannelFuture connect() {
        // 连接指定的地址和端口
        ChannelFuture connect = clientBootstrap.connect(host, port);
        connect.awaitUninterruptibly();
        return connect;
    }

    @Override
    public void close() {
        group.shutdownGracefully();
    }
}
 