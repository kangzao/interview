package com.jep.github.interview.jvm;

public class ClassInitTest {
    private static int num = 1;
    private static int number = 10; //linking之prepare: number = 0 --> initial: 10 --> 20

    static {
        num = 2;
        number = 20;
        System.out.println(num);
        System.out.println(number);
    }

    public static void main(String[] args) {
        System.out.println(ClassInitTest.num); //2
        System.out.println(ClassInitTest.number); //2
    }
}