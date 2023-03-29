package com.jep.github.classloader;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * 兔年春节倒计时
 */
public class CountDown {

  public static void main(String[] args) {
    // 1.获取当前时间
    LocalDateTime nowTime = LocalDateTime.now();
    // 2.设置结束时间(2023兔年春节的具体时间)
    LocalDateTime endTime = LocalDateTime.of(2023, 01, 22, 00, 00, 00);
    // 3. until是获取两个时间相差的年月日时分秒...
    long days = nowTime.until(endTime, ChronoUnit.DAYS);
    // 开始时间添加计算到的天数
    nowTime = nowTime.plusDays(days);
    long hours = nowTime.until(endTime, ChronoUnit.HOURS);
    nowTime = nowTime.plusHours(hours);
    long minutes = nowTime.until(endTime, ChronoUnit.MINUTES);
    nowTime = nowTime.plusMinutes(minutes);
    long seconds = nowTime.until(endTime, ChronoUnit.SECONDS);
    if (!(seconds < 0)) {
      String outputStr =
          "🐇" + "                                   距离2023兔年春节还有：" + days + "天" + hours + "小时"
              + minutes + "分" + seconds + "秒                                    " + "🐇";
      System.out.println(
          "🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇");
      System.out.println(
          "🐇                                                                                                        🐇");
      System.out.println(
          "🐇                                                                                                        🐇");
      System.out.println(
          "🐇                                                                                                        🐇");
      System.out.println(outputStr);
      System.out.println(
          "🐇                                                                                                        🐇");
      System.out.println(
          "🐇                                                                                                        🐇");
      System.out.println(
          "🐇                                                                                                        🐇");
      System.out.println(
          "🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇🐇");
    } else {
      System.out.println("🐇兔年至，祝您春节快乐！");
    }

  }

}

