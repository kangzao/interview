package com.jep.github.rpc.demo.BeanUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * bean管理器
 *
 * @author enping.jep
 * @date 2024/07/29 15:12
 **/
public class BeanManager {
    private static Map<String, Object> services = new ConcurrentHashMap<>();

    public static void registerBean(String serviceName, Object bean) {
        services.put(serviceName, bean);
    }

    public static Object getBean(String serviceName) {
        return services.get(serviceName);
    }
}
 