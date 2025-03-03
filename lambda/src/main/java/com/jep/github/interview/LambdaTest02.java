package com.jep.github.interview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * 将函数作为参数进行传递
 */
public class LambdaTest02 {

    /**
     * 根据给定的规则，过滤集合中的字符串，此规则由 Predicate 的方法决定
     */
    public static List<String> filterString(List<String> list, Predicate<String> pre) {
        ArrayList<String> filterList = new ArrayList<>();
        for (String s : list) {
            if (pre.test(s)) {
                filterList.add(s);
            }
        }

        return filterList;
    }

    public static void main(String[] args) {
        List<String> list = Arrays.asList("北京", "南京", "天津", "东京", "西京");

        List<String> list2 = filterString(list, s -> s.contains("京"));  // 或者 s->s.contaions("京"); // 数据类型可以省略，一条语句 return {}也可以省略

        System.out.println(list2);

    }
}
