package com.jep.core.polymorphism;

public class PassByValueReference {
    // 一个简单的类
    static class Point {
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "Point(" + x + ", " + y + ")";
        }
    }

    public static void main(String[] args) {
        Point pt1 = new Point(1, 2);
        // 值传递基本数据类型
        swapValues(pt1.x, pt1.y);
        System.out.println("After swapValues: " + pt1.x + ", " + pt1.y);

        // 引用传递对象
        changePoint(pt1);


        System.out.println("After changePoint: " + pt1);
    }

    // 这个方法尝试交换两个int值
    static void swapValues(int a, int b) {
        int temp = a;
        a = b;
        b = temp;
        System.out.println("Inside swapValues: " + a + ", " + b);
    }

    // 这个方法修改Point对象的坐标
    static void changePoint(Point p) {
        p.x = 10;
        p.y = 20;
    }
}