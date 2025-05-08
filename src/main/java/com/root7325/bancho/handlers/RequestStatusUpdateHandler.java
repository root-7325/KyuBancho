package com.root7325.bancho.handlers;

import com.root7325.bancho.core.BanchoSession;
import com.root7325.bancho.enums.Completeness;
import com.root7325.bancho.packets.AbstractPacket;
import com.root7325.bancho.packets.impl.UserStatsPacket;

/**
 * @author kate on 04.05.2025
 */
public class RequestStatusUpdateHandler implements IHandler {
    @Override
    public void handle(AbstractPacket packet, BanchoSession session) {
        UserStatsPacket statsPacket = new UserStatsPacket(session.getUser(), session.getUserStatus());
        session.write(statsPacket);
        session.flush();
    }
}
