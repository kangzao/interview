package com.jep.github.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.Buffer;

/*
 * @author: enping.jep
 * @create: 2021-11-05 2:48 下午
 * 实现一个简单的从客户端发送一个消息到服务端的功能
 */
public class SocketClient {


  public static void main(String[] args) {
    Socket socket = null;
    PrintWriter out = null;
    try {
      //向本机端口发出客户请求
      socket = new Socket("127.0.0.1", 8080);
      //由系统标准输入设备构造BufferedReader对象
      BufferedReader sin = new BufferedReader(new InputStreamReader(System.in));
      //由socket对象得到输出流，并构造PrintWriter对象
      PrintWriter os = new PrintWriter(socket.getOutputStream());
      //由socket对象得到输入流，并构造相应的BufferedReader对象
      BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      String readline;
      readline = sin.readLine();//从系统标准输入读入字符串
      //若从标准输入读入的字符串为bye则停止循环
      while (!readline.equals("bye")) {
        //将从系统标准输入读入的字符串输出到server
        os.println(readline);
        //刷新输出流，使server马上收到该字符串
        os.flush();
        //打印读入的字符串
        System.out.println("Client：" + readline);
        //从server读入字符串，并打印
        System.out.println("从server读入字符串: " + is.readLine());

        readline = sin.readLine();//从系统标准输入读入字符串

      }
      os.close();
      is.close();
      socket.close();
    } catch (Exception e) {
      System.out.println("Error" + e);
    }
  }
}
