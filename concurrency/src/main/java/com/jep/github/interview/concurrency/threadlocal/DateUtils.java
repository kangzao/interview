package com.jep.github.interview.concurrency.threadlocal;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 模拟并发环境下使用SimpleDateFormat的parse方法将字符串转换成Date对象
     *
     * @param stringDate
     * @return
     * @throws Exception
     */
    public static Date parseDate(String stringDate) throws Exception {
        return sdf.parse(stringDate);
    }

    public static void main(String[] args) throws Exception {
        for (int i = 1; i < 30; i++) {
            new Thread(() -> {
                try {
                    System.out.println(DateUtils.parseDate("2022-11-11 11:11:11"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }
    }
}