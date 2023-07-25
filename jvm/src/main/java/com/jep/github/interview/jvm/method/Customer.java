package com.jep.github.interview.jvm.method;

public class Customer {
    int id = 13;
    String name;
    Account acct;

    {
        name = "zzh";
    }

    public Customer() {
        acct = new Account();
    }

    public static void main(String[] args) {
        Customer user = new Customer();
        System.out.println(user);
    }

}


class Account {

}