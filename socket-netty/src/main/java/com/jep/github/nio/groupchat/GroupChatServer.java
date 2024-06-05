package com.jep.github.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

public class GroupChatServer {

    //定义属性
    private Selector selector;
    private ServerSocketChannel listenChannel;

    private static final int PORT = 6667;

    //构造器
    //初始化工作
    public GroupChatServer() {
        try {
            //得到选择器
            selector = Selector.open();
            //ServerSocketChannel
            listenChannel = ServerSocketChannel.open();
            //绑定端口
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            //设置非阻塞模式
            listenChannel.configureBlocking(false);
            //将通道（Channel）注册到 Selector上,并指定感兴趣的事件类型。
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen() {
        try {
            //循环处理
            while (true) {
                //这个方法会阻塞，直到至少有一个已注册的通道就绪，或者当前线程被中断，或者选择器被关闭。如果方法返回，它将返回一个整数，表示已就绪的通道数量
                int count = selector.select();
                if (count > 0) { //有事件处理
                    // 遍历得到 selectionKey 集合,SelectionKey 代表了已就绪的通道和它们的事件
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        //SelectionKey 是 Java NIO 库中的一个类，它表示一个注册到 Selector 上的通道（Channel）的键（Key）。每个 SelectionKey 都与一个特定的 Selector
                        //和一个特定的通道（如 SocketChannel、ServerSocketChannel 等）相关联，并且包含了一些状态信息，这些信息表明通道是否准备好进行读取、写入或连接操作。
                        SelectionKey key = iterator.next();
                        //监听到 accept 如果通道是 ServerSocketChannel 并且准备好接收新连接，则返回 true
                        if (key.isAcceptable()) {
                            SocketChannel sc = listenChannel.accept();
                            sc.configureBlocking(false);
                            //将该 sc 注册到 seletor
                            sc.register(selector, SelectionKey.OP_READ);
                            //提示
                            System.out.println(sc.getRemoteAddress() + " 上线 ");
                        }
                        if (key.isReadable()) {//通道发送read事件，即通道是可读的状态 如果通道是可读的，则返回 true。
                            // 处理读(专门写方法..)
                            readData(key);
                        }
                        //当前的 key 删除，防止重复处理
                        iterator.remove();
                    }
                } else {
                    System.out.println("等待....");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //发生异常处理....
        }
    }

    //读取客户端消息
    public void readData(SelectionKey key) {
        SocketChannel channel = null;
        try {
            //得到 channel
            channel = (SocketChannel) key.channel();
            //创建 buffer
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            //从网络套接字通道中读取数据到一个 ByteBuffer 对象
            //返回值表示实际读取的字节数。如果返回0，表示没有更多数据可读，但这并不意味着通道已经关闭。如果返回-1，表示通道已经关闭或者已经到达流的末尾（EOF）。
            int count = channel.read(buffer);
            //根据 count 的值做处理
            if (count > 0) {
                //把缓存区的数据转成字符串
//                String msg = new String(buffer.array(),StandardCharsets.UTF_8);  把数据读取到了长度为1024的byte类型的数组中，而实际上只有XXXX这些你想要的数据，剩下的数据如果都读取出来就是一堆方框。改正的办法是:
                String msg = new String(buffer.array(), 0, count, StandardCharsets.UTF_8);
                //输出该消息
                System.out.println("form客户端:" + msg);
                //向其它的客户端转发消息(去掉自己),专门写一个方法来处理
                sendInfoToOtherClients(msg, channel);
            }
        } catch (IOException e) {
            try {
                System.out.println(channel.getRemoteAddress() + "离线了..");
                //取消注册
                key.cancel();
                //关闭通道
                channel.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }

    //转发消息给其它客户(通道)
    private void sendInfoToOtherClients(String msg, SocketChannel self) throws IOException {

        System.out.println("服务器转发消息中...");
        //遍历所有注册到 selector 上的 SocketChannel,并排除 self
        //Selector#keys() 方法是 Java NIO 中 Selector 类的一个方法，它返回当前处于就绪状态的 SelectionKey 集合。
        //这个方法是同步的，意味着它会阻塞调用线程，直到至少有一个 SelectionKey 变为可操作状态，或者直到超时时间到达。
        for (SelectionKey key : selector.keys()) {
            //通过 key 取出对应的 SocketChannel
            Channel targetChannel = key.channel();
            //排除自己
            if (targetChannel instanceof SocketChannel && targetChannel != self) {
                //转型
                SocketChannel dest = (SocketChannel) targetChannel;
                //将 msg 存储到 buffer
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                //将 buffer 的数据写入通道
                dest.write(buffer);
            }
        }
    }

    public static void main(String[] args) {
        //创建服务器对象
        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.listen();
    }
}