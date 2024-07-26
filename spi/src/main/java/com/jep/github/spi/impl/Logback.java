package com.jep.github.spi.impl;

import com.jep.github.spi.Log;

public class Logback implements Log {

    @Override
    public void log(String info) {
        System.out.println("Logback:" + info);
    } 

} 