package com.jep.github.rpc.demo.test;

/**
 * @author enping.jep
 * @date 2024/07/29 19:27
 **/
public class DemoServiceImpl implements DemoService {
    public String sayHello(String param) {
        System.out.println("param" + param);
        return "hello:" + param;
    }
}