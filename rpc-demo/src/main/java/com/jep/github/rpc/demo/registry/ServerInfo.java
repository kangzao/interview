package com.jep.github.rpc.demo.registry;

import lombok.Data;

/**
 * 服务端信息
 *
 * @author enping.jep
 * @date 2024/07/26 18:32
 **/
@Data
public class ServerInfo {
    private String host;
    private int port;
}
 