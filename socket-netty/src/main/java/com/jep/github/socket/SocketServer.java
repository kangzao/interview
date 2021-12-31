package com.jep.github.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * @author: enping.jep
 * @create: 2021-11-05 11:13 上午
 * 实现一个简单的从客户端发送一个消息到服务端的功能
 */
public class SocketServer {


  public static void main(String[] args) {
    ServerSocket serverSocket = null;
    Socket socket = null;
    try {

//      TCP 的服务端要先监听一个端口，一般是先调用
//      bind 函数，给这个 Socket 赋予一个 IP 地址和端
//      口。为什么需要端口呢?要知道，你写的是一个应用
//      程序，当一个网络包来的时候，内核要通过 TCP 头里
//      面的这个端口，来找到你这个应用程序，把包给你。
//      为什么要 IP 地址呢?有时候，一台机器会有多个网
//      卡，也就会有多个 IP 地址，你可以选择监听所有的
//      网卡，也可以选择监听一个网卡，这样，只有发给这
//      个网卡的包，才会给你。
      serverSocket = new ServerSocket(8080);
      //阻塞等待客户端连接，服务端调用 accept 函数，拿出一个已经完成的连接进行处理。如果还没有完成，就要等着。

      socket = serverSocket.accept();
    } catch (IOException e) {
      e.printStackTrace();
    }
    String line;
    try {
      //由socket对象得到输入流，并构造相应的BufferedReader
      BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      //由socket对象得到输出流，并构造PrintWriter对象
      PrintWriter os = new PrintWriter(socket.getOutputStream());
      //由系统标准输入设备构造BufferedReader对象
      BufferedReader sin = new BufferedReader(new InputStreamReader(System.in));
      //从标准输出上打印从客户端读入的字符串
      System.out.println("receive Client msg:" + is.readLine());
      //从标准输入读入一个字符串
      line = sin.readLine();
      //如果字符串为bye则停止循环
      while (!line.equals("bye")) {
//        向客户端输出该字符串
        os.println(line);
        // 刷新输出流，使client马上收到该字符串
        os.flush();
        // 在系统标准输出上打印读入的字符串
        System.out.println("Server:" + line);
        // 从client读入字符串，并打印到标准输出
        System.out.println("从client读入字符串:" + is.readLine());
        //从标准输入读入一个字符串
        line = sin.readLine();
      }
      os.close();//关闭socket输出流
      is.close();//关闭socket输入流
      socket.close();//关闭socket
      serverSocket.close();//关闭ServerSocket
    } catch (IOException e) {
      System.out.println("Error:" + e);
      e.printStackTrace();
    }
  }
}

