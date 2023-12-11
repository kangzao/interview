package com.jep.github.netty.protocal;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.logging.LoggingHandler;

public class CodesMainTest {
    public static void main(String[] args) throws Exception {
        EmbeddedChannel channel = new EmbeddedChannel(
                new LoggingHandler(),
                new MessageRecordEncoder(),//出站
                new MessageRecordDecode());//入站
        Header header = new Header();
        header.setSessionId(123456);
        header.setType(OpCode.PING.code());
        MessageRecord record = new MessageRecord();
        record.setBody("Hello World");
        record.setHeader(header);
        channel.writeOutbound(record);

        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        new MessageRecordEncoder().encode(null, record, buf);
        channel.writeInbound(buf);
    }
}
