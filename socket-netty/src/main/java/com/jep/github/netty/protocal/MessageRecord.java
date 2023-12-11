package com.jep.github.netty.protocal;

import lombok.Data;

@Data
public class MessageRecord {

    private Header header;
    private Object body;
}
