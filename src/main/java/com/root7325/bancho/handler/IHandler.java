package com.root7325.bancho.handler;

import com.root7325.bancho.core.BanchoSession;
import com.root7325.bancho.packet.AbstractPacket;

/**
 * @author kate on 02.05.2025
 */
public interface IHandler {
    void handle(AbstractPacket packet, BanchoSession session);
}
