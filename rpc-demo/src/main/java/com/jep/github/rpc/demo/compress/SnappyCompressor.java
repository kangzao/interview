package com.jep.github.rpc.demo.compress;

import org.xerial.snappy.Snappy;

import java.io.IOException;

/**
 * 基于 Snappy 压缩算法
 *
 * @author enping.jep
 * @date 2024/07/29 12:03
 **/
public class SnappyCompressor implements Compressor {

    public byte[] compress(byte[] array) throws IOException {
        if (array == null) {
            return null;
        }
        return Snappy.compress(array);
    }

    public byte[] unCompress(byte[] array) throws IOException {
        if (array == null) {
            return null;
        }
        return Snappy.uncompress(array);
    }
}
 