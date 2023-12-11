package com.jep.github.netty.protocal;

import lombok.Data;

@Data
public class Header {
    private long sessionId; //会话id  : 占8个字节
    private byte type; //消息类型： 占1个字节
    private int length;     //消息长度 : 占4个字节
}
