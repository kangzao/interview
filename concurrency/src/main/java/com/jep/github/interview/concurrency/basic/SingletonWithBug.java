package com.jep.github.interview.concurrency.basic;

public class SingletonWithBug {
    private static SingletonWithBug myinstance;

    public static SingletonWithBug getInstance() {//池化思想的体现
        if (myinstance == null) { //线程B
            synchronized (SingletonWithBug.class) {
                if (myinstance == null) {
                    myinstance = new SingletonWithBug();//Thread A
                    // jvm三步操作，非原子操作(有可能部分成功、或者部分失败) 中间状态
                    // 开辟内存
                    // 内存上初始化对象 integer n = 4  INSTANCE -> SingletonWithBug
                    // 内存赋值给instance    instance指向内存 //线程A
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

//MVC   model  view  controller
//有状态 无状态
//user 有状态的  id  id
//UserController  （C -R -U -d） 没有状态的
//UserDao  没有状态的

//UserController  UserDao  只需要一份就够了 不需要频繁被初始化
//无状态的 工具类 controller service  dao高频使用的，往往只加载一次 放入到jvm内存里
//避免了每次 new controller
//JVM
