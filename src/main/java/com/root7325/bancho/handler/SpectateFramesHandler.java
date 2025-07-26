package com.root7325.bancho.handler;

import com.root7325.bancho.core.BanchoSession;
import com.root7325.bancho.packet.AbstractPacket;
import com.root7325.bancho.packet.PacketType;
import com.root7325.bancho.packet.impl.SpectateFramesPacket;
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

        manager.handleSpectateFrames(framesPacket);
    }
}
