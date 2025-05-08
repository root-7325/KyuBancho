package com.root7325.netty.codec;

import com.root7325.bancho.packets.AbstractPacket;
import com.root7325.bancho.packets.PacketFactory;
import com.root7325.bancho.packets.PacketType;
import com.root7325.utils.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author kate on 02.05.2025
 */
@Slf4j
@RequiredArgsConstructor
public class PacketDecoder extends ByteToMessageDecoder {
    private final PacketFactory packetFactory;

    public PacketDecoder() {
        this.packetFactory = new PacketFactory();
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() < AbstractPacket.HEADER_SIZE) {
            return;
        }

        int type = byteBuf.readUnsignedShortLE();
        byteBuf.readByte(); // compression
        int length = byteBuf.readIntLE();

        if (byteBuf.readableBytes() < length) {
            internalBuffer().resetReaderIndex();
            return;
        }
        ByteBuf in = byteBuf.readBytes(length);
        AbstractPacket abstractPacket = packetFactory.create(PacketType.values()[type]); // todo: !!!!

        if (abstractPacket != null) {
            abstractPacket.readFromStream(in);
            list.add(abstractPacket);
            log.debug("Decoded {} packet!", abstractPacket.getPacketType());
        } else {
            log.debug("Unknown packet with type {}!", type);
        }
    }
}
