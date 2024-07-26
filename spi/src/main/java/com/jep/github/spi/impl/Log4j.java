package com.jep.github.spi.impl;

import com.jep.github.spi.Log;

public class Log4j implements Log {

    @Override
    public void log(String info) {
        System.out.println("Log4j:" + info);
    } 

} 