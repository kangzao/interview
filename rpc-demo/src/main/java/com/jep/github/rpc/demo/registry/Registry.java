package com.jep.github.rpc.demo.registry;

import org.apache.curator.x.discovery.ServiceInstance;

import java.util.List;

/**
 * 注册接口
 *
 * @author enping.jep
 * @date 2024/07/26 18:32
 **/
public interface Registry<T> {

    void registerService(ServiceInstance<T> service) throws Exception;

    void unregisterService(ServiceInstance<T> service) throws Exception;

    List<ServiceInstance<T>> queryForInstances(String name) throws Exception;
}
