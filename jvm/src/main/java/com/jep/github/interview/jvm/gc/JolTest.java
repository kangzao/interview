package com.jep.github.interview.jvm.gc;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author Ian
 * @date 2023/08/10 15:50
 **/
public class JolTest {
    public static void main(String[] args) {
        User user = new User();
        System.out.println(ClassLayout.parseInstance(user).toPrintable());
    }

}

class User {
    private String name;
    private Integer age;
    private boolean sex;

    private Order order;

}

class Order {
    private long orderId;
}
