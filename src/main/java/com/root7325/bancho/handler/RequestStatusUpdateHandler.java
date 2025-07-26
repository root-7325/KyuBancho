package com.root7325.bancho.handler;

import com.root7325.bancho.core.BanchoSession;
import com.root7325.bancho.packet.AbstractPacket;
import com.root7325.bancho.packet.impl.UserStatsPacket;

/**
 * @author kate on 04.05.2025
 */
public class RequestStatusUpdateHandler implements IHandler {
    @Override
    public void handle(AbstractPacket packet, BanchoSession session) {
        UserStatsPacket statsPacket = new UserStatsPacket(session.getUser(), session.getUserStatus());
        session.writeAndFlush(statsPacket);
    }
}
