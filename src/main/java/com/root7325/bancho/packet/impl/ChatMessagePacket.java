package com.root7325.bancho.packet.impl;

import com.root7325.bancho.packet.AbstractPacket;
import com.root7325.utils.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author root7325 on 09.06.2025
 */
@Getter
@Setter
@NoArgsConstructor
public class ChatMessagePacket extends AbstractPacket {
    private String sender;
    private String target;
    private String content;

    public boolean isPrivate() {
        return target.isEmpty() || target.charAt(0) != '#';
    }

    @Override
    public void writeToStream(ByteBuf out) {
        ByteBufUtils.writeString(sender, out);
        ByteBufUtils.writeString(content, out);
        ByteBufUtils.writeString(target, out);
    }

    @Override
    public void readFromStream(ByteBuf in) {
        this.sender = ByteBufUtils.readString(in);
        this.content = ByteBufUtils.readString(in);
        this.target = ByteBufUtils.readString(in);
    }
}
