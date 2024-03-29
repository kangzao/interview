package com.jep.github.netty.protocal;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.List;

@Slf4j
public class MessageRecordDecode extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        MessageRecord record = new MessageRecord();
        Header header = new Header();
        header.setSessionId(byteBuf.readLong());  //读取8个字节的sessionid
        header.setType(byteBuf.readByte()); //读取一个字节的操作类型
        record.setHeader(header);
        //如果byteBuf剩下的长度还有大于4个字节，说明body不为空
        if (byteBuf.readableBytes() >= 4) {
            int length = byteBuf.readInt(); //读取四个字节的长度
            header.setLength(length);
            byte[] contents = new byte[length];
            byteBuf.readBytes(contents, 0, length);
            ByteArrayInputStream bis = new ByteArrayInputStream(contents);
            ObjectInputStream ois = new ObjectInputStream(bis);
            record.setBody(ois.readObject());
            list.add(record);
            log.info("序列化出来的结果：" + record);
        } else {
            log.error("消息内容为空");
        }
    }
}
