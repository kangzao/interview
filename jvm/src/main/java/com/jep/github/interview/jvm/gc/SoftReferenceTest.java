package com.jep.github.interview.jvm.gc;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ian
 * @date 2023/08/01 18:00
 **/
public class SoftReferenceTest {
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("软引用被回收了");    // oom前会执行清除软引用
    }

    public static void main(String[] args) {
        // 声明一个软引用
        SoftReference<SoftReferenceTest> softTest = new SoftReference<>(new SoftReferenceTest());
        List list = new ArrayList();
        while (true) {
            byte[] b = new byte[1024 * 1024 * 1];
            list.add(b);
        }
    }
}
