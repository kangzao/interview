package com.jep.github.rpc.demo.protocol;

import lombok.Data;

/**
 * 消息头对应的实体类
 * @author enping.jep
 * @date 2024/07/26 17:53
 **/
@Data
public class Header {
    private short magic; // 魔数
    private byte version; // 版本号
    private byte extraInfo; // 附加信息
    private Long messageId; // 消息ID
    private Integer size; // 消息体长度

    public Header(short magic, byte version) {
        this.magic = magic;
        this.version = version;
        this.extraInfo = 0;
    }

    public Header(short magic, byte version, byte extraInfo, Long messageId, Integer size) {
        this.magic = magic;
        this.version = version;
        this.extraInfo = extraInfo;
        this.messageId = messageId;
        this.size = size;
    }
}
 