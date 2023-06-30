package com.jep.github.interview.lambda;

import java.util.function.Consumer;

public class LambdaTest01 {

    public static void happyTime(double money, Consumer<Double> consumer) {
        consumer.accept(money);
    }

    // 匿名实现接口 传递接口实例
    public static void main(String[] args) {


        happyTime(600, (Double d) -> System.out.println("Hello World:" + d));
    }


}