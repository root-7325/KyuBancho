package com.root7325.bancho.packets.impl.generic;

import com.root7325.bancho.packets.AbstractPacket;
import com.root7325.bancho.packets.PacketType;
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
