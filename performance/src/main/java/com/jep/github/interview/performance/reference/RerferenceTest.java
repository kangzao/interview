package com.jep.github.interview.performance.reference;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * @author enping.jep
 * @date 2023/11/03 16:47
 **/
public class RerferenceTest {
    public static void main(String[] args) {
        //强引用
        String str = new String("123");
        //软引用
        SoftReference<String> softReference = new SoftReference<String>(str);
        //释放强引用
        str = null;
        System.gc();
        System.out.println(softReference.get());
        //强引用
        String str2 = new String("123");
        WeakReference<String> weakReference = new WeakReference<String>(str2);
        //释放强引用
        str2 = null;
        System.gc();
        System.out.println(weakReference.get());

    }
}
