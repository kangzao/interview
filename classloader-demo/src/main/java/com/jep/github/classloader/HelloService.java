package com.jep.github.classloader;

/**
 * @author enping
 * @date 2021/5/13 9:12 下午
 **/
public class HelloService {

    public static String value = getValue();

    static {
        System.out.println("########static code");
    }

    private static String getValue() {
        System.out.println("#####static method");
        return "jep";
    }

    public void test() {
        System.out.println("test:" + value);
    }
}
