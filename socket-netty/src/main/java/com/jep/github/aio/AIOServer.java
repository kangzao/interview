package com.jep.github.aio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.net.InetSocketAddress;

/*
 * @author: enping.jep
 * @create: 2022-01-12 3:59 下午
 */
public class AIOServer {

  private final int port;

  public static void main(String args[]) {
    int port = 8000;
    new AIOServer(port);
  }

  public AIOServer(int port) {
    this.port = port;
    listen();
  }

  private void listen() {
    try {
//    线程池长度为无限大，创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
      ExecutorService executorService = Executors.newCachedThreadPool();
      AsynchronousChannelGroup threadGroup = AsynchronousChannelGroup.withCachedThreadPool(
          executorService, 1);
      //开门营业
      //工作线程，用来侦听回调的，事件响应的时候需要回调
      final AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open(
          threadGroup);
      server.bind(new InetSocketAddress(port));
      System.out.println("服务已启动，监听端口" + port);

      //准备接受数据
      server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
        final ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

        //实现completed方法来回调
        //由操作系统来触发
        //回调有两个状态，成功
        public void completed(AsynchronousSocketChannel result, Object attachment) {
          System.out.println("IO操作成功，开始获取数据");
          try {
            buffer.clear();
            result.read(buffer).get();
            buffer.flip();
            result.write(buffer);
            buffer.flip();
          } catch (Exception e) {
            System.out.println(e.toString());
          } finally {
            try {
              result.close();
              server.accept(null, this);
            } catch (Exception e) {
              System.out.println(e.toString());
            }
          }

          System.out.println("操作完成");
        }

        @Override
        //回调有两个状态，失败
        public void failed(Throwable exc, Object attachment) {
          System.out.println("IO操作失败: " + exc);
        }
      });

      try {
        Thread.sleep(Integer.MAX_VALUE);
      } catch (InterruptedException ex) {
        System.out.println(ex);
      }
    } catch (IOException e) {
      System.out.println(e);
    }
  }
}
