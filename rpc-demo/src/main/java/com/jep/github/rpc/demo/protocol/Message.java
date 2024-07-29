package com.jep.github.rpc.demo.protocol;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 消息体
 *
 * @author enping.jep
 * @date 2024/07/26 18:14
 **/
@Data
@AllArgsConstructor
public class Message<T> {
    private Header header;
    private T content;
}
 