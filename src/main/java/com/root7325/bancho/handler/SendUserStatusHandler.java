package com.root7325.bancho.handler;

import com.root7325.bancho.core.BanchoSession;
import com.root7325.bancho.core.ServiceLocator;
import com.root7325.bancho.packet.AbstractPacket;
import com.root7325.bancho.packet.impl.SendUserStatusPacket;
import com.root7325.bancho.packet.impl.UserStatsPacket;

/**
 * @author kate on 04.05.2025
 */
public class SendUserStatusHandler implements IHandler {
    @Override
    public void handle(AbstractPacket packet, BanchoSession session) {
        SendUserStatusPacket statusPacket = (SendUserStatusPacket) packet;

        session.setUserStatus(statusPacket.getStatus());
        UserStatsPacket userStatsPacket = new UserStatsPacket(session.getUser(), statusPacket.getStatus());
        ServiceLocator.getInstance()
                .getPacketDispatcherService()
                .broadcast(userStatsPacket);
    }
}
