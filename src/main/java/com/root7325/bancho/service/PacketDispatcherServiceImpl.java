package com.root7325.bancho.service;

import com.google.inject.Inject;
import com.root7325.bancho.core.BanchoSession;
import com.root7325.bancho.core.ISessionManager;
import com.root7325.bancho.packet.AbstractPacket;
import com.root7325.bancho.service.interfaces.IPacketDispatcherService;
import lombok.AllArgsConstructor;

/**
 * @author kate on 03.05.2025
 */
@AllArgsConstructor(onConstructor = @__({@Inject}))
public class PacketDispatcherServiceImpl implements IPacketDispatcherService {
    private final ISessionManager sessionManager;

    @Override
    public void sendPacket(BanchoSession session, AbstractPacket packet) {
        session.write(packet);
        session.flush();
    }

    @Override
    public void broadcast(AbstractPacket packet) {
        sessionManager.getSessions().forEach(session -> sendPacket(session, packet));
    }

    @Override
    public void broadcastExcept(AbstractPacket packet, BanchoSession... excludedSessions) {
        throw new UnsupportedOperationException("broadcastExcept method is not implemented!");
    }
}
