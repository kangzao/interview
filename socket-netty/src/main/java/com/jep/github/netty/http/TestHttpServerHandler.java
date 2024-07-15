package com.jep.github.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * 说明
 * 1. SimpleChannelInboundHandler 是 ChannelInboundHandlerAdapter
 * 2. HttpObject 客户端和服务器端相互通讯的数据被封装成 HttpObject
 */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if(msg instanceof HttpRequest){ //判断该  HttpObject msg 参数是否是 Http 请求
            System.out.println(ctx.channel().remoteAddress() + " 客户端请求数据 ... ");

            // 准备给客户端浏览器发送的数据
            ByteBuf byteBuf = Unpooled.copiedBuffer("Hello Client", CharsetUtil.UTF_8);

            // 设置 HTTP 版本, 和 HTTP 的状态码, 返回内容
            DefaultFullHttpResponse defaultFullHttpResponse =
                    new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                            HttpResponseStatus.OK, byteBuf);

            // 设置 HTTP 请求头
            // 设置内容类型是文本类型
            defaultFullHttpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            // 设置返回内容的长度
            defaultFullHttpResponse.headers().set(
                    HttpHeaderNames.CONTENT_LENGTH,
                    byteBuf.readableBytes());

            // 写出 HTTP 数据
            ctx.writeAndFlush(defaultFullHttpResponse);
        }
    }
}
