package com.jep.core.polymorphism;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
    public static void main(String[] args) throws ParseException {
        Animal animal = new Animal();
        Animal dog = new Dog();

        // 调用 Animal 类中的原始方法
        animal.sound();

        // 调用重载的方法，传入参数
        animal.sound("strange");

        // 由于多态性，dog 对象调用的将是 Dog 类中重写的方法
        dog.sound();

    }
}