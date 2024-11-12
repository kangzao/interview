package com.jep.core.polymorphism;

class Animal {
    // 父类中的原始方法
    public void sound() {
        System.out.println("Animal makes a sound");
    }

    // 重载的另一个版本，接受一个参数
    public void sound(String type) {
        System.out.println("Animal makes a " + type + " sound");
    }


    public static void main(String[] args) {
        Animal animal = new Animal();
        System.out.println(animal);
        int i = 0;
        // 在这里编写您的程序代码
        i++;
    }


}