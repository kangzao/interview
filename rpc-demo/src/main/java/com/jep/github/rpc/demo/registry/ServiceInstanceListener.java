package com.jep.github.rpc.demo.registry;

import org.apache.curator.x.discovery.ServiceInstance;

/**
 * @author enping.jep
 * @date 2024/07/29 19:20
 **/
public interface ServiceInstanceListener<T> {
    void onRegister(ServiceInstance<T> serviceInstance);

    void onRemove(ServiceInstance<T> serviceInstance);

    void onUpdate(ServiceInstance<T> serviceInstance);

    void onFresh(ServiceInstance<T> serviceInstance, ServerInfoEvent event);

    enum ServerInfoEvent {
        ON_REGISTER,
        ON_UPDATE,
        ON_REMOVE
    }
}
