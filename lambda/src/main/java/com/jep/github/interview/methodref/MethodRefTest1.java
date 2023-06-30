package com.jep.github.interview.methodref;

import java.util.function.Supplier;

public class MethodRefTest1 {
    /**
     * Supplier 中的 T get()
     * Employee 中的String getName()  两者的方法结构是一样的。
     */
    public static void main(String[] args) {
        // 匿名实现接口的方式：
        Employee emp = new Employee(1001, "Tom", 23, 5600);
        Supplier<String> supplier1 = new Supplier<String>() {
            @Override
            public String get() {
                return emp.getName();
            }
        };
        String regStr1 = supplier1.get();  // 调用其 Supplier 重写的get()抽象方法
        System.out.println(regStr1);

        // Lambda 表达式
        Supplier<String> supplier2 = () -> emp.getName();
        String regStr2 = supplier2.get();  // 调用其 Supplier 重写的get()抽象方法
        System.out.println(regStr2);


        // 方法引用
        Supplier<String> supplier3 = emp::getName;
        String regStr3 = supplier3.get();  // 调用其 Supplier 重写的get()抽象方法
        System.out.println(regStr3);
    }
}

class Employee {
    private int id;
    private String name;
    private int age;

    private int salary;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public Employee(int id, String name, int age, int salary) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.salary = salary;
    }
}