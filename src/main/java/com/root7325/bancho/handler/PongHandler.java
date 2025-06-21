package com.root7325.bancho.handler;

import com.root7325.bancho.core.BanchoSession;
import com.root7325.bancho.packet.AbstractPacket;

/**
 * @author kate on 04.05.2025
 */
public class PongHandler implements IHandler {
    @Override
    public void handle(AbstractPacket packet, BanchoSession session) {
        session.setLastPongTime(System.currentTimeMillis());
    }
}
