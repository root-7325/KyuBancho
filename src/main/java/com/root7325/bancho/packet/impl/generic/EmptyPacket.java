package com.root7325.bancho.packet.impl.generic;

import com.root7325.bancho.packet.AbstractPacket;
import com.root7325.bancho.packet.PacketType;
import io.netty.buffer.ByteBuf;
import lombok.NoArgsConstructor;

/**
 * @author kate on 02.05.2025
 */
@NoArgsConstructor
public class EmptyPacket extends AbstractPacket {

    public EmptyPacket(PacketType packetType) {
        this.setPacketType(packetType);
    }

    @Override
    public void readFromStream(ByteBuf in) {

    }

    @Override
    public void writeToStream(ByteBuf out) {

    }
}
