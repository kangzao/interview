package com.jep.github.interview.concurrency.threadDemo;

import java.util.concurrent.TimeUnit;

public class InterruptTest {
    public static void main(String[] args) throws Exception {
        // sleepThread 不停的尝试睡眠
        Thread st = new Thread(new SleepRunner(), "SleepThread");
        st.setDaemon(true);//设置成精灵线程，其他非精灵线程都结束后，jvm自动关闭
        st.start();

        // busyThread 不停的运行
        Thread bt = new Thread(new BusyRunner(), "BusyThread");
        bt.setDaemon(true);
        bt.start();

        // 休眠 2 秒，让 sleepThread 和 busyThread 充分运行
        TimeUnit.SECONDS.sleep(2);

        st.interrupt();
        bt.interrupt();

        System.out.println("SleepThread interrupted is " + st.isInterrupted());

        // do something
        System.out.println("BusyThread interrupted is " + bt.isInterrupted());

        // 防止 sleepThread 和 busyThread 立刻退出
        TimeUnit.SECONDS.sleep(40);
    }

    static class SleepRunner implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("sleeping in SleepRunner");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class BusyRunner implements Runnable {
        @Override
        public void run() {
            while (true) {
            }
        }
    }
}