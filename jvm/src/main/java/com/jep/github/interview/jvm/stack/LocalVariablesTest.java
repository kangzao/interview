package com.jep.github.interview.jvm.stack;

import java.util.Date;

public class LocalVariablesTest {
    private int count = 0;

    public static void main(String[] args) {
        LocalVariablesTest test = new LocalVariablesTest();
        int num = 10;
        test.test1();
    }

    public void test1() {
        int n;
        Date date = new Date();
        String name1 = "hello stack";
        test2(date, name1);
        System.out.println(date + name1);
    }

    public String test2(Date dateP, String name2) {

        dateP = null;
        name2 = "xiaoming";
        double weight = 130.5;//占据两个slot
        char gender = '男';
        return dateP + name2;
    }
}