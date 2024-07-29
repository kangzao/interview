package com.jep.github.rpc.demo.transport;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.jep.github.rpc.demo.Constants;
import com.jep.github.rpc.demo.protocol.Message;
import com.jep.github.rpc.demo.protocol.Request;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 服务端处理器
 *
 * @author enping.jep
 * @date 2024/07/29 15:06
 **/
public class DemoRpcServerHandler extends SimpleChannelInboundHandler<Message<Request>> {

    // 业务线程池
    private static Executor executor = Executors.newCachedThreadPool();

    @Override
    protected void channelRead0(final ChannelHandlerContext channelHandlerContext, Message<Request> message) throws Exception {
        byte extraInfo = message.getHeader().getExtraInfo();
        if (Constants.isHeartBeat(extraInfo)) { // 心跳消息，直接返回即可
            channelHandlerContext.writeAndFlush(message);
            return;
        }
        // 非心跳消息，直接封装成Runnable提交到业务线程池
        executor.execute(new InvokeRunnable(message, channelHandlerContext));
    }
}
 