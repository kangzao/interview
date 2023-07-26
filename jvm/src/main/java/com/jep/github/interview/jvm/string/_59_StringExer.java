package com.jep.github.interview.jvm.string;

public class _59_StringExer {
    String str = new String("good");

    String test = "test";
    char[] ch = {'t', 'e', 's', 't'};

    public void change(String str, char ch[]) {
        str = "test ok";
        ch[0] = 'b';
    }

    public static void main(String[] args) {
        _59_StringExer ex = new _59_StringExer();
        ex.change(ex.str, ex.ch);
        System.out.println(ex.str);//good  不变
        System.out.println(ex.ch);//best  变
    }
}