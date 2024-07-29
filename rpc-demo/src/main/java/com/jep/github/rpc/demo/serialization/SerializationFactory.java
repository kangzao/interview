package com.jep.github.rpc.demo.serialization;

/**
 * 工厂模式
 *
 * @author enping.jep
 * @date 2024/07/29 13:55
 **/
public class SerializationFactory {
    public static Serialization get(byte type) {
        switch (type & 0x7) {
            case 0x0:
                return new HessianSerialization();
            default:
                return new HessianSerialization();
        }

    }
}
 