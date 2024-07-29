package com.jep.github.rpc.demo.compress;

import java.io.IOException;

/**
 * 压缩算法
 * 在有的场景中，请求或响应传输的数据比较大，直接传输比较消耗带宽，所以一般会采用压缩后再发送的方式
 * @author enping.jep
 * @date 2024/07/29 12:02
 **/
public interface Compressor {
    byte[] compress(byte[] array) throws IOException;

    byte[] unCompress(byte[] array) throws IOException;
}
