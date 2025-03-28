package com.jep.github.netty.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        ByteBuf delimiter = Unpooled.copiedBuffer("\t".getBytes());
        //入站
        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(2048, delimiter));
        //入站
        pipeline.addLast("decoder", new StringDecoder());
        //出站
        pipeline.addLast("encoder", new StringEncoder());

        // 自己的逻辑Handler 入站
        pipeline.addLast("handler", new ServerHandler());
    }
}
