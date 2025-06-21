package com.root7325.bancho.packet.impl.generic;

import com.root7325.bancho.packet.AbstractPacket;
import com.root7325.bancho.packet.PacketType;
import com.root7325.utils.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author kate on 02.05.2025
 */
@NoArgsConstructor
@Getter
public class StringPacket extends AbstractPacket {
    private String s;

    public StringPacket(PacketType type, String s) {
        this.setPacketType(type);
        this.s = s;
    }

    @Override
    public void readFromStream(ByteBuf in) {
        this.s = ByteBufUtils.readNETString(in);
    }

    @Override
    public void writeToStream(ByteBuf out) {
        ByteBufUtils.writeString(s, out);
    }
}
