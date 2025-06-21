package com.root7325.bancho.handler;

import com.google.inject.Inject;
import com.root7325.bancho.core.BanchoSession;
import com.root7325.bancho.packet.AbstractPacket;
import com.root7325.bancho.packet.impl.SendUserStatusPacket;
import com.root7325.bancho.packet.impl.UserStatsPacket;
import com.root7325.bancho.service.interfaces.IPacketDispatcherService;
import lombok.AllArgsConstructor;

/**
 * @author kate on 04.05.2025
 */
@AllArgsConstructor(onConstructor = @__({@Inject}))
public class SendUserStatusHandler implements IHandler {
    private final IPacketDispatcherService packetDispatcherService;

    @Override
    public void handle(AbstractPacket packet, BanchoSession session) {
        SendUserStatusPacket statusPacket = (SendUserStatusPacket) packet;

        session.setUserStatus(statusPacket.getStatus());
        UserStatsPacket userStatsPacket = new UserStatsPacket(session.getUser(), statusPacket.getStatus());
        packetDispatcherService.broadcast(userStatsPacket);
    }
}
