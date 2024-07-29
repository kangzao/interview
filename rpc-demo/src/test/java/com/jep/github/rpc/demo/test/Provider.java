package com.jep.github.rpc.demo.test;

import com.jep.github.rpc.demo.BeanUtil.BeanManager;
import com.jep.github.rpc.demo.registry.ServerInfo;
import com.jep.github.rpc.demo.registry.ZookeeperRegistry;
import com.jep.github.rpc.demo.transport.DemoRpcServer;
import org.apache.curator.x.discovery.ServiceInstance;

/**
 * @author enping.jep
 * @date 2024/07/29 19:26
 **/
public class Provider {
    public static void main(String[] args) throws Exception {
        // 创建DemoServiceImpl，并注册到BeanManager中
        BeanManager.registerBean("demoService", new DemoServiceImpl());
        // 创建ZookeeperRegistry，并将Provider的地址信息封装成ServerInfo
        // 对象注册到Zookeeper
        ZookeeperRegistry<ServerInfo> discovery = new ZookeeperRegistry<>();
        discovery.start();
        ServerInfo serverInfo = new ServerInfo("127.0.0.1", 20880);
        discovery.registerService(
                ServiceInstance.<ServerInfo>builder().name("demoService").payload(serverInfo).build());
        // 启动DemoRpcServer，等待Client的请求
        DemoRpcServer rpcServer = new DemoRpcServer(20880);
        rpcServer.start();
        Thread.sleep(100000000L);
    }
}
 