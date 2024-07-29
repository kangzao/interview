package com.jep.github.rpc.demo.serialization;

import java.io.IOException;

/**
 * 序列化接口
 *
 * @author enping.jep
 * @date 2024/07/29 11:59
 **/
public interface Serialization {

    <T> byte[] serialize(T obj) throws IOException;

    <T> T deserialize(byte[] data, Class<T> clz) throws IOException;
}
