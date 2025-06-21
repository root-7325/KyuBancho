package com.root7325.netty.codec;

import com.root7325.bancho.packet.AbstractPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author kate on 03.05.2025
 */
@Slf4j
public class PacketEncoder extends MessageToByteEncoder<AbstractPacket> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, AbstractPacket abstractPacket, ByteBuf byteBuf) {
        ByteBuf payload = channelHandlerContext.alloc().buffer();
        abstractPacket.writeToStream(payload);

        byteBuf.writeShortLE(abstractPacket.getPacketType().ordinal());
        byteBuf.writeByte(0);
        byteBuf.writeIntLE(payload.writerIndex());
        byteBuf.writeBytes(payload);
    }
}
