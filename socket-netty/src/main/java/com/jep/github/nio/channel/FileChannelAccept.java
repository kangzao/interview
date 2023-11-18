package com.jep.github.nio.channel;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author enping.jep
 * @date 2023/10/31 14:00
 **/
public class FileChannelAccept {
    public static final String GREETING = "Hello java nio.\r\n";

    public static void main(String[] argv) throws Exception {
        int port = 1234; // default
        if (argv.length > 0) {
            port = Integer.parseInt(argv[0]);
        }
        ByteBuffer buffer = ByteBuffer.wrap(GREETING.getBytes());
//        通过调用 ServerSocketChannel.open() 方法来打开 ServerSocketChannel
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.socket().bind(new InetSocketAddress(port));
        ssc.configureBlocking(false);
        while (true) {
            System.out.println("Waiting for connections");
//            通过 ServerSocketChannel.accept() 方法监听新进的连接。
//            当accept()方法返回时候,它返回一个包含新进来的连接的 SocketChannel。
//            因此, accept()方法会一直阻塞到有新连接到达
            SocketChannel sc = ssc.accept();
            if (sc == null) {
                System.out.println("null");
                Thread.sleep(2000);
            } else {
                System.out.println("Incoming connection from: " +
                        sc.socket().getRemoteSocketAddress());
                buffer.rewind();
                sc.write(buffer);
                sc.close();
            }
        }
    }
}

