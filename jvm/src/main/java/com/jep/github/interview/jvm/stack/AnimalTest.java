package com.jep.github.interview.jvm.stack;

/**
 * 说明早期绑定和晚期绑定的例子
 */
class Animal {
    public void eat() {
        System.out.println("动物进食");
    }
}

interface Huntable {
    void hunt();
}

class Dog extends Animal implements Huntable {
    @Override
    public void eat() {
        System.out.println("狗吃骨头");
    }

    @Override
    public void hunt() {
        System.out.println("捕食耗子，多管闲事");
    }
}

class Cat extends Animal implements Huntable {
    public Cat() {
        super();//表现为：早期绑定（确定调用父类的构造方法）
    }

    public Cat(String name) {
        this();//表现为：早期绑定（调用的就是没有参数的构造方法，而没有参数的构造方法就是调用了父类的构造方法）
    }

    @Override
    public void eat() {
        super.eat();//表现为：早期绑定（非常确定调用的是父类的eat方法）
        System.out.println("猫吃鱼");
    }

    @Override
    public void hunt() {
        System.out.println("捕食耗子，天经地义");
    }
}

public class AnimalTest {
    public void showAnimal(Animal animal) {
        animal.eat();//表现为：晚期绑定（只是知道动物，但具体是什么动物还不知道）
    }

    public void showHunt(Huntable h) {
        h.hunt();//表现为：晚期绑定（接口本身就不能实例化，具体是哪个实现类也不知道）
    }
}