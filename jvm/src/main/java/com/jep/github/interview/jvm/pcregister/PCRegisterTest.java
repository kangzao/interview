package com.jep.github.interview.jvm.pcregister;

/**
 * @author Ian
 * @date 2023/07/05 16:59
 **/
public class PCRegisterTest {

    public static void main(String[] args) throws InterruptedException {
        int i = 10;
        int j = 20;
        int k = i + j;

        String s = "abc";
        System.out.println(i);

        Thread.sleep(1000000);
        System.out.println(k);
    }
}
