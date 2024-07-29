package com.jep.github.rpc.demo.transport;

import com.jep.github.rpc.demo.protocol.Message;

import io.netty.channel.Channel;
import io.netty.util.concurrent.Promise;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 异步结果
 *
 * @author enping.jep
 * @date 2024/07/29 14:27
 **/
@Data
@AllArgsConstructor
public class NettyResponseFuture<T> {
    private long createTime;
    private long timeOut;
    private Message<T> request;
    private Channel channel;
    private Promise<T> promise;
}
 