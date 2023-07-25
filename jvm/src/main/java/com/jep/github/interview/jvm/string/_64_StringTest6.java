package com.jep.github.interview.jvm.string;

import org.junit.Test;

/**
 * 字符串拼接操作
 */
public class _64_StringTest6 {
    @Test
    public void test1() {
        String s1 = "a" + "b" + "c";//编译期优化：等同于"abc"
        String s2 = "abc"; //"abc"一定是放在字符串常量池中，将此地址赋给s2
        /*
         * 最终.java编译成.class，再反编译.class文件可以看到s1、s2如下：
         * String s1 = "abc";
         * String s2 = "abc"
         */
        System.out.println(s1 == s2); //true
        System.out.println(s1.equals(s2)); //true

        /**
         * 从字节码看：
         *  0 ldc #2 <abc>     // 将常量池中指定的常量放入操作数栈中,这里
         *  2 astore_1         // 将栈顶引用型数值存入第二个本地变量
         *  3 ldc #2 <abc>     // #2可以看出来，s1、s2的常量地址一样，即他们指向的是常量池中同一个常量
         *  5 astore_2
         */
    }

    @Test
    public void test2() {
        String s1 = "javaEE";
        String s2 = "hadoop";

        String s3 = "javaEEhadoop";
        String s4 = "javaEE" + "hadoop";//编译期优化
        //如果拼接符号的前后出现了变量，则相当于在堆空间中new String()，具体的内容为拼接的结果：javaEEhadoop
        String s5 = s1 + "hadoop";  // 是new了一个对象
        String s6 = "javaEE" + s2;  // 是new了一个对象
        String s7 = s1 + s2;  // 是new了一个对象

        System.out.println(s3 == s4);//true
        System.out.println(s3 == s5);//false
        System.out.println(s3 == s6);//false
        System.out.println(s3 == s7);//false
        System.out.println(s5 == s6);//false
        System.out.println(s5 == s7);//false
        System.out.println(s6 == s7);//false
        //intern():判断字符串常量池中是否存在javaEEhadoop值，如果存在，则返回常量池中javaEEhadoop的地址；
        //如果字符串常量池中不存在javaEEhadoop，则在常量池中加载一份javaEEhadoop，并返回此对象的地址。
        String s8 = s6.intern();    // s6指向堆中一个字符串为javaEEhadoop"的对象，只是获取s6字符串的值，s8其实指向的是s3所指向的对象
        System.out.println(s3 == s8);//true    s3指向常量池的"javaEEhadoop"，常量池的"javaEEhadoop"是在s3赋值的时候创建的
        System.out.println(s6 == s8);//false    注意这俩不是同一个对象
    }

    @Test
    public void test3() {
        String s1 = "a";
        String s2 = "b";
        String s3 = "ab";

        /*
        如下的s1 + s2 的执行细节：(变量s是我临时定义的）
        ① StringBuilder s = new StringBuilder();
        ② s.append("a")
        ③ s.append("b")
        ④ s.toString()  --> 约等于 new String("ab")，可以看源码 -- 新new出来的对象地址给s4

        补充：在jdk5.0之后使用的是StringBuilder（线程不安全，但速度快）,在jdk5.0之前使用的是StringBuffer（线程安全，但速度慢）

        字节码层面看：
         0 ldc #14 <a>
         2 astore_1
         3 ldc #15 <b>
         5 astore_2
         6 ldc #16 <ab>
         8 astore_3
         9 new #9 <java/lang/StringBuilder>                      // 有变量就new StringBuilder
        12 dup
        13 invokespecial #10 <java/lang/StringBuilder.<init>>
        16 aload_1                                               // 加载"a"
        17 invokevirtual #11 <java/lang/StringBuilder.append>    // append("a")
        20 aload_2
        21 invokevirtual #11 <java/lang/StringBuilder.append>    // append("b")
        24 invokevirtual #12 <java/lang/StringBuilder.toString>  // 返回字符串
        27 astore 4
        29 getstatic #3 <java/lang/System.out>
         */
        String s4 = s1 + s2;//
        System.out.println(s3 == s4);//false

    }

    /*
    1. 字符串拼接操作不一定使用的是StringBuilder!
       如果拼接符号左右两边都是字符串常量或常量引用，则仍然使用编译期优化，即非StringBuilder的方式。
    2. 针对于final修饰类、方法、基本数据类型、引用数据类型的量的结构时，能使用上final的时候建议使用上（编译期间就能确定）。
     */
    @Test
    public void test4() {
        // final修饰后就是常量了，就不是变量了
        final String s1 = "a";
        final String s2 = "b";
        String s3 = "ab";
        // 编译期间就能确定s4 = "ab"，反编译class文件为java源文件后可以看到 String s4 =  "ab"; 字节码可以看到 ldc #16 <ab>
        String s4 = s1 + s2;
        System.out.println(s3 == s4);//true
    }

    //练习：
    @Test
    public void test5() {
        String s1 = "javaEEhadoop";
        String s2 = "javaEE";
        String s3 = s2 + "hadoop";
        System.out.println(s1 == s3);//false

        final String s4 = "javaEE";//s4:常量
        String s5 = s4 + "hadoop";
        System.out.println(s1 == s5);//true

    }

    /*
    体会执行效率：通过StringBuilder的append()的方式添加字符串的效率要远高于使用String的字符串拼接方式！
    详情：① StringBuilder的append()的方式：自始至终中只创建过一个StringBuilder的对象
          使用String的字符串拼接方式：创建过多个StringBuilder和String的对象
         ② 使用String的字符串拼接方式：内存中由于创建了较多的StringBuilder和String的对象，内存占用更大；如果进行GC，需要花费额外的时间。

     改进的空间：在实际开发中，如果基本确定要前前后后添加的字符串长度不高于某个限定值highLevel的情况下,建议使用构造器实例化：
               StringBuilder s = new StringBuilder(highLevel);//new char[highLevel] //（指定容量大小，避免多次扩容）
     */
    @Test
    public void test6() {

        long start = System.currentTimeMillis();

        // 分别执行method1、method2方法，对比String、StringBuilder拼接字符串的效率。-- 结果可以看到String效率差StringBuilder一千倍
        method1(100000);// 耗时：4435毫秒
//        method2(100000); // 耗时：4毫秒

        long end = System.currentTimeMillis();

        System.out.println("花费的时间为：" + (end - start));
    }

    public void method1(int highLevel) {
        String src = "";
        for (int i = 0; i < highLevel; i++) {
            src = src + "a";//每次循环都会创建一个StringBuilder、String（每次会创建两个对象）
        }
//        System.out.println(src);

    }

    public void method2(int highLevel) {
        //只需要创建一个StringBuilder
        StringBuilder src = new StringBuilder();
        for (int i = 0; i < highLevel; i++) {
            src.append("a");
        }
//        System.out.println(src);
    }
}