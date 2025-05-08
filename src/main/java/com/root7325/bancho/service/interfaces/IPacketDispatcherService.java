package com.root7325.bancho.service.interfaces;

import com.root7325.bancho.core.BanchoSession;
import com.root7325.bancho.packets.AbstractPacket;

/**
 * @author kate on 03.05.2025
 */
public interface IPacketDispatcherService {
    void sendPacket(BanchoSession session, AbstractPacket packet);
    void broadcast(AbstractPacket packet);
    void broadcastExcept(AbstractPacket packet, BanchoSession... excludedSessions);
}
