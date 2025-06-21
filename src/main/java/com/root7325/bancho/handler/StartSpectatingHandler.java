package com.root7325.bancho.handler;

import com.root7325.bancho.core.BanchoSession;
import com.root7325.bancho.core.SessionManager;
import com.root7325.bancho.packet.AbstractPacket;
import com.root7325.bancho.packet.PacketType;
import com.root7325.bancho.packet.impl.generic.IntPacket;
import com.root7325.bancho.packet.impl.generic.StringPacket;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

/**
 * @author root7325 on 07.06.2025
 */
@Slf4j
public class StartSpectatingHandler implements IHandler {
    @Override
    public void handle(AbstractPacket packet, BanchoSession session) {
        int targetId = ((IntPacket) packet).getI();
        log.debug("{} is trying to start spectating {}", session.getUser().getId(), targetId);

        Optional<BanchoSession> optionalSession = getTargetSession(targetId);
        optionalSession
                .map(BanchoSession::getStreamingManagerService)
                .ifPresentOrElse(
                        manager -> {
                            manager.handleNewSpectator(session);
                            notifyJoin(session.getUser().getId(), manager.getHost(), manager.getSpectators());
                        },
                        () -> session.writeAndFlush(new StringPacket(PacketType.Bancho_Announce, "Target unavailable."))
                );
    }

    private Optional<BanchoSession> getTargetSession(int id) {
        SessionManager manager = SessionManager.getInstance();
        return manager.getSession(id);
    }

    private void notifyJoin(int id, BanchoSession target, List<BanchoSession> spectators) {
        target.writeAndFlush(new IntPacket(PacketType.Bancho_SpectatorJoined, id));
        spectators.forEach(session -> session.writeAndFlush(new IntPacket(PacketType.Bancho_FellowSpectatorJoined, id)));
    }
}
