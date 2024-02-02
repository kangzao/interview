package com.jep.github.interview.performance;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class EventBusCenter {
    private static EventBus eventBus = new EventBus();

    private EventBusCenter() {
    }

    public static EventBus getInstance() {
        return eventBus;
    }

    public static void register(Object obj) {
        eventBus.register(obj);
    }

    public static void unregister(Object obj) {
        eventBus.unregister(obj);
    }

    public static void post(Object obj) {
        eventBus.post(obj);
    }

    public static void main(String[] args) throws InterruptedException {
        DataObserver1 observer1 = new DataObserver1();
        DataObserver2 observer2 = new DataObserver2();
        EventBusCenter.register(observer1);
        EventBusCenter.register(observer2);
        System.out.println("============   start  ====================");
        // 只有注册的参数类型为String的方法会被调用
        EventBusCenter.post("post string method");
        EventBusCenter.post(123);
        System.out.println("============ after unregister ============");
        // 注销observer2
        EventBusCenter.unregister(observer2);
        EventBusCenter.post("post string method");
        EventBusCenter.post(123);
        System.out.println("============    end           =============");
    }
}

class DataObserver1 {
    /**
     * 只有通过@Subscribe注解的方法才会被注册进EventBus
     * 而且方法有且只能有1个参数
     *
     * @param msg
     */
    @Subscribe
    public void func(String msg) {
        System.out.println("String msg: " + msg);
    }
}

class DataObserver2 {
    /**
     * post() 不支持自动装箱功能,只能使用Integer,不能使用int,否则handlersByType的Class会是int而不是Intege
     * 而传入的int msg参数在post(int msg)的时候会被包装成Integer,导致无法匹配到
     */
    @Subscribe
    public void func(Integer msg) {
        System.out.println("Integer msg: " + msg);
    }
}