package com.jep.github.netty.protocal;

//定义操作类型
public enum OpCode {

    BIZ_REQ((byte) 0),
    BIZ_RESP((byte) 1),
    PING((byte) 3),
    PONG((byte) 4);

    private byte code;

    private OpCode(byte code) {
        this.code = code;
    }

    public byte code() {
        return this.code;
    }
}
