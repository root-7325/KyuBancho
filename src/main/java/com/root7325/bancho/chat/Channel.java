package com.root7325.bancho.chat;

import com.root7325.bancho.core.BanchoSession;
import com.root7325.bancho.packet.PacketType;
import com.root7325.bancho.packet.impl.generic.StringPacket;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author root7325 on 21.04.2024
 */
@Slf4j
@Getter
public class Channel {
    private final String name;
    private final List<BanchoSession> participants;
    private final boolean isPrivate;

    public Channel(String name) {
        this.name = name;
        this.participants = new ArrayList<>();
        this.isPrivate = !name.startsWith("#");
    }

    public void newParticipant(BanchoSession session) {
        if (!participants.contains(session)) {
            participants.add(session);
            session.writeAndFlush(new StringPacket(PacketType.Bancho_ChannelJoinSuccess, name));
        }
    }
}
