package com.root7325.netty.codec;

import com.root7325.bancho.packet.AbstractPacket;
import com.root7325.bancho.packet.PacketFactory;
import com.root7325.bancho.packet.PacketType;
import com.root7325.utils.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
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
        if (!hasEnoughBytesForHeader(byteBuf)) {
            return;
        }

        PacketHeader header = readPacketHeader(byteBuf);
        if (!hasEnoughBytesForBody(byteBuf, header.getLength())) {
            internalBuffer().resetReaderIndex();
            return;
        }

        ByteBuf in = readBody(byteBuf, header);
        processPacket(header.getType(), in, list);
    }

    private boolean hasEnoughBytesForHeader(ByteBuf byteBuf) {
        return byteBuf.readableBytes() >= AbstractPacket.HEADER_SIZE;
    }

    private PacketHeader readPacketHeader(ByteBuf byteBuf) {
        int type = byteBuf.readUnsignedShortLE();
        boolean compressed = (byteBuf.readByte() == 1);
        int length = byteBuf.readIntLE();
        return new PacketHeader(type, compressed, length);
    }

    private boolean hasEnoughBytesForBody(ByteBuf byteBuf, int length) {
        return byteBuf.readableBytes() >= length;
    }

    private ByteBuf readBody(ByteBuf byteBuf, PacketHeader header) throws IOException {
        ByteBuf payload = byteBuf.readBytes(header.length);

        if (header.isCompressed()) {
            return ByteBufUtils.decompress(payload);
        }
        return payload;
    }

    private void processPacket(int type, ByteBuf in, List<Object> list) {
        PacketType packetType = getPacketType(type);
        if (packetType == null) {
            return;
        }

        AbstractPacket abstractPacket = packetFactory.create(packetType);
        if (abstractPacket != null) {
            abstractPacket.readFromStream(in);
            list.add(abstractPacket);
            log.debug("Decoded {} packet!", abstractPacket.getPacketType());
        } else {
            log.debug("Unknown packet with type {}!", type);
        }
    }

    @Getter
    @AllArgsConstructor
    private static class PacketHeader {
        private final int type;
        private final boolean compressed;
        private final int length;
    }

    private PacketType getPacketType(int type) {
        try {
            return PacketType.values()[type];
        } catch (ArrayIndexOutOfBoundsException ex) {
            log.warn("Corrupted packet type: {}", type);
            return null;
        }
    }
}
