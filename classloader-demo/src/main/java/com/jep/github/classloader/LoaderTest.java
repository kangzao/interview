package com.jep.github.classloader;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * 验证不会重复加载类
 * 指定class 进行加载e
 */
public class LoaderTest {
    public static void main(String[] args) throws Exception {
//        File file = new File("file name with spaces.txt");
//
//        URI fileUri = file.toURI();
//        System.out.println("URI:" + fileUri);
//
//        URL fileUrl = file.toURI().toURL();
//        System.out.println("URL:" + fileUrl);
//
//        URL fileUrlWithoutSpecialCharacterHandling = file.toURL();
//        System.out.println("URL (no special character handling):" + fileUrlWithoutSpecialCharacterHandling);



        URL classUrl = new URL("file:///Users/enping/code/interview/classloader-demo/target/classes");//jvm 类放在位置

        URLClassLoader parentLoader = new URLClassLoader(new URL[]{classUrl});

        while (true) {
            // 创建一个新的类加载器
            URLClassLoader loader = new URLClassLoader(new URL[]{classUrl});

            // 问题：静态块触发
            Class clazz = loader.loadClass("HelloService");
            System.out.println("HelloService所使用的类加载器：" + clazz.getClassLoader());

            Object newInstance = clazz.newInstance();
            Object value = clazz.getMethod("test").invoke(newInstance);
            System.out.println("调用getValue获得的返回值为：" + value);

            Thread.sleep(3000L); // 1秒执行一次
            System.out.println();

            //  help gc  -verbose:class
            newInstance = null;
            loader = null;

        }

        // System.gc();
    }
}
