package com.root7325.bancho.handlers;

import com.root7325.bancho.core.BanchoSession;
import com.root7325.bancho.packets.AbstractPacket;

/**
 * @author kate on 02.05.2025
 */
public interface IHandler {
    void handle(AbstractPacket packet, BanchoSession session);
}
