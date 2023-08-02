package com.jep.github.interview.jvm.gc;

import java.util.ArrayList;
import java.util.List;

public class _79_StopTheWorldDemo {
    public static class WorkThread extends Thread {
        List<byte[]> list = new ArrayList<byte[]>();

        public void run() {
            try {
                while (true) {
                    for (int i = 0; i < 1000; i++) {
                        byte[] buffer = new byte[1024];
                        list.add(buffer);
                    }

                    if (list.size() > 10000) {
                        list.clear();
                        //会触发full gc，进而会出现STW事件。 出现STW就会卡顿，打印的时间就不是每隔一秒打印一次了
                        System.gc();
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }


    public static class PrintThread extends Thread {
        public final long startTime = System.currentTimeMillis();


        public void run() {
            try {
                while (true) {
                    // 每秒打印时间信息
                    long t = System.currentTimeMillis() - startTime;
                    System.out.println(t / 1000 + "." + t % 1000);
                    Thread.sleep(1000);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        WorkThread w = new WorkThread();
        PrintThread p = new PrintThread();
        w.start();  //打开注释 运行结果，出现STW就会卡顿，打印的时间就不是每隔一秒打印一次了，这间接证明了STW的存在
        p.start();//可以看到没有STW的过程，打印的时间相邻之间的间隔基本都是1秒
    }
}