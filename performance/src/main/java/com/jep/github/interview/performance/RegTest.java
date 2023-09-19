package com.jep.github.interview.performance;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author enping.jep
 * @date 2023/09/18 17:45
 **/
public class RegTest {
    public static void main(String[] args) {
        String text = "<input high=\"20\" weight=\"70\">test</input>";
        String reg = "(?:<input.*?>)(.*?)(?:</input>)";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(text);
        while (m.find()) {
            System.out.println(m.group(0));// 整个匹配到的内容
            System.out.println(m.group(1));//(.*?)
        }
    }
}
