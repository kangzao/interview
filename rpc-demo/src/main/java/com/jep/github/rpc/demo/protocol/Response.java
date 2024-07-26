package com.jep.github.rpc.demo.protocol;

import java.io.Serializable;

import lombok.Data;

/**
 * 响应体
 *
 * @author enping.jep
 * @date 2024/07/26 18:13
 **/
@Data
public class Response implements Serializable {

    private int code = 0; // 响应的错误码，正常响应为0，非0表示异常响应

    private String errMsg; // 异常信息

    private Object result; // 响应结果
}