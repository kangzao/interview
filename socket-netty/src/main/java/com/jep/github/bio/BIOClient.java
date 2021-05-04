package com.jep.github.bio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @author enping
 * @date 2021/5/1 下午10:14
 **/
public class BIOClient {
    private static Charset charset = Charset.forName("UTF-8");

    public static void main(String[] args) throws IOException {
        Socket s = new Socket("localhost", 8080);
        OutputStream out = s.getOutputStream();

        Scanner scanner = new Scanner(System.in);
        System.out.println("input：");
        String msg = scanner.nextLine();
        out.write(msg.getBytes(charset));//阻塞 写完成
        scanner.close();
        s.close();
    }

}
