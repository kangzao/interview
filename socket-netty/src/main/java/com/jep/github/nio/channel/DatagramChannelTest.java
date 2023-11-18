package com.jep.github.nio.channel;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.Charset;

/**
 * @author enping.jep
 * @date 2023/10/31 14:37
 **/
public class DatagramChannelTest {

    /**
     * 发包的 datagram
     *
     * @throws IOException
     * @throws InterruptedException
     */
    @Test
    public void sendDatagram() throws IOException, InterruptedException {
        DatagramChannel sendChannel = DatagramChannel.open();
        InetSocketAddress sendAddress = new InetSocketAddress("127.0.0.1", 9999);
        while (true) {
            sendChannel.send(ByteBuffer.wrap("发包".getBytes("UTF-8")), sendAddress);
            System.out.println("发包端发包");
            Thread.sleep(1000);
        }
    }

    /**
     * 收包端
     *
     * @throws IOException
     */
    @Test
    public void receive() throws IOException {
        DatagramChannel receiveChannel = DatagramChannel.open();
        InetSocketAddress receiveAddress = new InetSocketAddress(9999);
        receiveChannel.bind(receiveAddress);
        ByteBuffer receiveBuffer = ByteBuffer.allocate(512);
        while (true) {
            receiveBuffer.clear();
            SocketAddress sendAddress = receiveChannel.receive(receiveBuffer);
            receiveBuffer.flip();
            System.out.print(sendAddress.toString() + " ");
            System.out.println(Charset.forName("UTF-8").decode(receiveBuffer));
        }
    }

}