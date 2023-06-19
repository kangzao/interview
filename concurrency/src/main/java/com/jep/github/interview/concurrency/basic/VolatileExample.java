//package com.jep.github.interview.concurrency.basic;
//
//class VolatileExample {
////    int x = 0;
////    volatile boolean v = false;
//
//    public void writer() {
//        int x = 42;
//        v = true;
//    }
//
//    public void reader() {
//       int x = 42
//        if (v == true) {//V == false
//// 这里x会是多少呢？
//            x = ?
//        }
//    }
//    //volatile 题目都会做了
//    // 顺序性规则：单线程内，严格按照顺序执行 x = 42 发生在 v = true;之前
//    // volatile变量规则：写之后可以被立刻看到 写入内存，从内存读， 放弃cpu多级缓存
//    // A > B  B > C  A > C  传递性
//
//    //equals()
//    // 完美
//    /**
//     x = 42;  v = true;
//     threadb： 根据规则2，直接从主内存读取 true
//     x = 42
//     //触类旁通 加分项  举一反三
//     **/
//
//}