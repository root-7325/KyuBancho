package com.root7325.bancho.packet.impl.generic;

import com.root7325.bancho.packet.AbstractPacket;
import com.root7325.bancho.packet.PacketType;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author kate on 02.05.2025
 */
@Getter
@Setter
@NoArgsConstructor
public class IntPacket extends AbstractPacket {
    private int i;

    public IntPacket(PacketType type, int i) {
        this.setPacketType(type);
        this.i = i;
    }

    @Override
    public void readFromStream(ByteBuf in) {
        this.i = in.readIntLE();
    }

    @Override
    public void writeToStream(ByteBuf out) {
        out.writeIntLE(i);
    }
}
