package com.root7325.bancho.packet;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

/**
 * @author kate on 02.05.2025
 */
@Setter
@Getter
public abstract class AbstractPacket {
    public static int HEADER_SIZE = 7;
    private PacketType packetType;

    public abstract void readFromStream(ByteBuf in);
    public abstract void writeToStream(ByteBuf out);
}
