package com.root7325.bancho.handlers;

import com.root7325.bancho.core.BanchoSession;
import com.root7325.bancho.packets.AbstractPacket;
import com.root7325.bancho.packets.PacketType;
import com.root7325.bancho.packets.impl.SpectateFramesPacket;
import com.root7325.bancho.service.StreamingManagerService;

/**
 * @author root7325 on 07.06.2025
 */
public class SpectateFramesHandler implements IHandler {
    @Override
    public void handle(AbstractPacket packet, BanchoSession session) {
        StreamingManagerService manager = session.getStreamingManagerService();

        SpectateFramesPacket framesPacket = (SpectateFramesPacket) packet;
        framesPacket.setPacketType(PacketType.Bancho_SpectateFrames); // :DD

        manager.getSpectators().forEach(spectator -> spectator.writeAndFlush(framesPacket));
    }
}
