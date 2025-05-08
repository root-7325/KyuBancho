package com.root7325.bancho.packets.impl;

import com.root7325.bancho.packets.AbstractPacket;
import com.root7325.bancho.structures.UserStatus;
import io.netty.buffer.ByteBuf;
import lombok.Getter;

/**
 * @author kate on 04.05.2025
 */
@Getter
public class SendUserStatusPacket extends AbstractPacket {
    private UserStatus status;
    @Override
    public void readFromStream(ByteBuf in) {
        this.status = new UserStatus(in);
    }

    @Override
    public void writeToStream(ByteBuf out) {

    }
}
