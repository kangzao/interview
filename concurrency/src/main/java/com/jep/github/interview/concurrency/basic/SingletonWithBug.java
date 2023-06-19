package com.jep.github.interview.concurrency.basic;

public class SingletonWithBug {
    private static SingletonWithBug myinstance;

    public static SingletonWithBug getInstance() {
        if (myinstance == null) { //线程B
            synchronized (SingletonWithBug.class) {
                if (myinstance == null) {
                    myinstance = new SingletonWithBug();
                    // jvm三步操作，非原子操作(有可能部分成功、或者部分失败) 中间状态
                    // 开辟内存
                    // 内存赋值给instance    instance指向内存 //线程A
                    // 内存上初始化对象 integer n = 4  INSTANCE -> SingletonWithBug
                    // 执行的更快、
                }
            }
        }
        return myinstance;
    }

    public static void main(String[] args) {
        SingletonWithBug.getInstance();
    }
}