package com.jep.github.serialize;

import java.io.Serializable;

/**
 * @author enping
 * @date 2022/1/8 8:30 上午
 **/
public class User implements Serializable {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}