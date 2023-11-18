package com.jep.github.interview.performance.reference;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

/**
 * @author enping.jep
 * @date 2023/11/03 17:21
 **/
public class SoftRefTest {
    private static ReferenceQueue<MyObject> softQueue = new ReferenceQueue<>();//创建引用队列

    public static class MyObject {

        @Override
        protected void finalize() throws Throwable {
            super.finalize();
            //被回收时输出
            System.out.println("MyObject's finalize called");
        }

        @Override
        public String toString() {
            return "I am MyObject";
        }
    }

    public static class CheckRefQueue implements Runnable {
        Reference<MyObject> obj = null;

        @Override
        public void run() {
            try {
                obj = (Reference<MyObject>) softQueue.remove();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (obj != null) {
                System.out.println("Object for SoftReference is " + obj.get());
            }
        }
    }

    public static void main(String[] args) {
        MyObject object = new MyObject();
//       使用SoftReference构造这个MyObject对象的软引用softRef，并注册到softQueue引用队列  当softRef被回收时，会被加入softQueue队列
        SoftReference<MyObject> softRef = new SoftReference<>(object, softQueue);
        new Thread(new CheckRefQueue()).start();
        object = null;    //删除强引用  系统内对MyObject对象的引用只剩下软引用
        System.gc(); //显式调用GC
        System.out.println("After GC: Soft Get= " + softRef.get()); //通过软引用的get()方法，取得MyObject对象的引用，发现对象并未被回收，这说明GC在内存充足的情况下，不会回收软引用对象。
        System.out.println("分配大块内存");
        byte[] b = new byte[5 * 1024 * 928];//在这次GC后，softRef.get()不再返回MyObject对象，而是返回null，说明在系统内存紧张的情况下，软引用被回收。软引用被回收时，会被加入注册的引用队列。
        System.out.println("After new byte[]:Soft Get= " + softRef.get());
    }

}
