package com.jep.github.interview.jvm.gc;

import java.util.HashSet;

public class ChangeHashCode {
    public static void main(String[] args) {
        HashSet set = new HashSet();
        Person p1 = new Person(1001, "AA");
        Person p2 = new Person(1002, "BB");
        set.add(p1);
        set.add(p2);
        System.out.println(p1.hashCode()); //33111
        p1.name = "CC";
        System.out.println(p1.hashCode());//33175
        set.remove(p1);//p1查无此人
        System.out.println(set);//2个对象！
        Person p3 = new Person(1001, "CC");
        System.out.println(p3.hashCode());
//        当向HashSet中添加元素的时候
//        首先计算元素的hashcode值，然后用这个hashcode计算出这个元素的存储位置，
//        如果这个位置为空，就将元素添加进去；如果不为空，则用equals方法比较元素是否相等，相等就不添加，否则找一个空位添加。
        set.add(p3);
        System.out.println(set);
        set.add(new Person(1001, "AA"));
        System.out.println(set);
    }
}

class Person {
    int id;
    String name;

    public Person(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        if (id != person.id) return false;
        return name != null ? name.equals(person.name) : person.name == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Person{" + "id=" + id + ", name='" + name + '\'' + '}';
    }
}

