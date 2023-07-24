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

}

class Account {

}