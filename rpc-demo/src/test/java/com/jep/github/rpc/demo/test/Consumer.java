package com.jep.github.rpc.demo.test;

import com.jep.github.rpc.demo.proxy.DemoRpcProxy;
import com.jep.github.rpc.demo.registry.ServerInfo;
import com.jep.github.rpc.demo.registry.ZookeeperRegistry;

/**
 * @author enping.jep
 * @date 2024/07/29 19:27
 **/
public class Consumer {
    public static void main(String[] args) throws Exception {
        // 创建ZookeeperRegistr对象
        ZookeeperRegistry<ServerInfo> discovery = new ZookeeperRegistry<>();
        discovery.start();

        // 创建代理对象，通过代理调用远端Server
        DemoService demoService = DemoRpcProxy.newInstance(DemoService.class, discovery);
        // 调用sayHello()方法，并输出结果
        String result = demoService.sayHello("hello");
        System.out.println(result);
        // Thread.sleep(10000000L);
    }
}
 