package com.jep.github.rpc.demo.compress;

/**
 * 工厂模式
 *
 * @author enping.jep
 * @date 2024/07/29 14:03
 **/
public class CompressorFactory {

    public static Compressor get(byte extraInfo) {
        switch (extraInfo & 24) {
            case 0x0:
                return new SnappyCompressor();
            default:
                return new SnappyCompressor();
        }
    }
}
 