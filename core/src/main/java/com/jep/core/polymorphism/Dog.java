package com.jep.core.polymorphism;

class Dog extends Animal {
    // 重写父类中的 sound() 方法
    @Override
    public void sound() {
        System.out.println("Dog barks");
    }
}