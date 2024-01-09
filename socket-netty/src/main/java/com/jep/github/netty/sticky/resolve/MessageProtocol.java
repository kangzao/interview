package com.jep.github.netty.sticky.resolve;

import lombok.Data;

@Data
public class MessageProtocol {

    //内容
    private byte[] content;
    //长度
    private int len;

    public MessageProtocol(byte[] content) {
        this.content = content;
        //计算出长度并赋值
        this.len = content.length;
    }
}

