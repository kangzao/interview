package com.jep.github.rpc.demo.registry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 服务端信息
 *
 * @author enping.jep
 * @date 2024/07/26 18:32
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServerInfo {
    private String host;
    private int port;
}
 