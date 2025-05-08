package com.root7325.bancho.service;

import com.root7325.bancho.core.BanchoSession;
import com.root7325.bancho.core.SessionManager;
import com.root7325.bancho.packets.AbstractPacket;
import com.root7325.bancho.service.interfaces.IPacketDispatcherService;

/**
 * @author kate on 03.05.2025
 */
public class PacketDispatcherServiceImpl implements IPacketDispatcherService {
    @Override
    public void sendPacket(BanchoSession session, AbstractPacket packet) {
        session.write(packet);
        session.flush();
    }

    @Override
    public void broadcast(AbstractPacket packet) {
        SessionManager.getInstance().getSessions().forEach(session -> sendPacket(session, packet));
    }

    @Override
    public void broadcastExcept(AbstractPacket packet, BanchoSession... excludedSessions) {
        throw new UnsupportedOperationException("broadcastExcept method is not implemented!");
    }
}
