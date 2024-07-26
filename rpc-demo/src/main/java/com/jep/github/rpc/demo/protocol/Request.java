package com.jep.github.rpc.demo.protocol;

import java.io.Serializable;
import java.util.Arrays;

import lombok.Data;

/**
 * 请求体
 * @author enping.jep
 * @date 2024/07/26 18:10
 **/
@Data
public class Request  implements Serializable {
    private String serviceName; // 请求的Service类名

    private String methodName; // 请求的方法名称

    private Class[] argTypes; // 请求方法的参数类型

    private Object[] args; // 请求方法的参数

    public Request(String serviceName, String methodName, Object[] args) {
        this.serviceName = serviceName;
        this.methodName = methodName;
        this.args = args;
        this.argTypes = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            argTypes[i] = args[i].getClass();
        }
    }

    @Override
    public String toString() {
        return "Request{" +
                "serviceName='" + serviceName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", args=" + Arrays.toString(args) +
                '}';
    }
}
 