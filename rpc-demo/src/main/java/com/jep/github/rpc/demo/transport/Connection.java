package com.jep.github.rpc.demo.transport;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import com.jep.github.rpc.demo.Constants;
import com.jep.github.rpc.demo.protocol.Header;
import com.jep.github.rpc.demo.protocol.Message;
import com.jep.github.rpc.demo.protocol.Request;
import com.jep.github.rpc.demo.protocol.Response;

import io.netty.channel.ChannelFuture;
import io.netty.channel.DefaultEventLoop;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.Promise;
import lombok.Getter;

/**DemoRpcProxy
 * Client 端的 Connection，它是用来暂存已发送出去但未得到响应的请求，
 * 这样，在响应返回时，就可以查找到相应的请求以及 Future，从而将响应结果返回给上层业务逻辑
 * @author enping.jep
 * @date 2024/07/29 14:29
 **/
public class Connection implements Closeable {
    // 用于生成消息ID，全局唯一
    private final static AtomicLong ID_GENERATOR = new AtomicLong(0);

    // TODO 时间轮定时删除
    // Connection 中没有定时清理 IN_FLIGHT_REQUEST_MAP 集合的操作，在无法正常获取响应的时候，
    // 就会导致 IN_FLIGHT_REQUEST_MAP 不断膨胀，最终 OOM
    public final static Map<Long, NettyResponseFuture<Response>> IN_FLIGHT_REQUEST_MAP
            = new ConcurrentHashMap<>();

    @Getter
    private ChannelFuture future;

    private AtomicBoolean isConnected = new AtomicBoolean();

    public Connection() {
        this.isConnected.set(false);
        this.future = null;
    }

    public Connection(ChannelFuture future, boolean isConnected) {
        this.future = future;
        this.isConnected.set(isConnected);
    }

    public boolean isConnected() {
        return isConnected.get();
    }

    public void setIsConnected(boolean isConnected) {
        this.isConnected.set(isConnected);
    }

    public NettyResponseFuture<Response> request(Message<Request> message, long timeOut) {
        // 生成并设置消息ID
        long messageId = ID_GENERATOR.incrementAndGet();
        message.getHeader().setMessageId(messageId);
        // 创建消息关联的Future
        NettyResponseFuture responseFuture = new NettyResponseFuture(System.currentTimeMillis(),
                timeOut, message, future.channel(), new DefaultPromise(new DefaultEventLoop()));
        // 将消息ID和关联的Future记录到IN_FLIGHT_REQUEST_MAP集合中
        IN_FLIGHT_REQUEST_MAP.put(messageId, responseFuture);
        try {
            future.channel().writeAndFlush(message); // 发送请求
        } catch (Exception e) {
            // 发送请求异常时，删除对应的Future
            IN_FLIGHT_REQUEST_MAP.remove(messageId);
            throw e;
        }
        return responseFuture;
    }

    public boolean ping() {
        Header heartBeatHeader = new Header(Constants.MAGIC, Constants.VERSION_1);
        heartBeatHeader.setExtraInfo(Constants.HEART_EXTRA_INFO);
        Message message = new Message(heartBeatHeader, null);
        NettyResponseFuture<Response> request = request(message, Constants.DEFAULT_TIMEOUT);
        try {
            Promise<Response> await = request.getPromise().await();
            return await.get().getCode() == Constants.HEARTBEAT_CODE;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void close() throws IOException {
        future.channel().close();
    }
}