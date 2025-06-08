package com.root7325.bancho.handlers;

import com.root7325.bancho.core.BanchoSession;
import com.root7325.bancho.core.SessionManager;
import com.root7325.bancho.packets.AbstractPacket;
import com.root7325.bancho.packets.PacketType;
import com.root7325.bancho.packets.impl.generic.IntPacket;
import com.root7325.bancho.packets.impl.generic.StringPacket;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

/**
 * @author root7325 on 07.06.2025
 */
@Slf4j
public class StopSpectatingHandler implements IHandler {
    @Override
    public void handle(AbstractPacket packet, BanchoSession session) {
        if (session.getSpectatingSubject() <= 0) {
            return;
        }

        Optional<BanchoSession> optionalSession = getTargetSession(session.getSpectatingSubject());
        optionalSession
                .map(BanchoSession::getStreamingManagerService)
                .ifPresent(
                        manager -> {
                            manager.handleLeftSpectator(session);
                            notifyLeft(session.getUser().getId(), manager.getHost(), manager.getSpectators());
                        }
                );
        session.setSpectatingSubject(-1);
        log.debug("{} stopped spectating {}!", session.getUser().getId(), session.getSpectatingSubject());
    }

    private Optional<BanchoSession> getTargetSession(int id) {
        SessionManager manager = SessionManager.getInstance();
        return manager.getSession(id);
    }

    private void notifyLeft(int id, BanchoSession target, List<BanchoSession> spectators) {
        target.writeAndFlush(new IntPacket(PacketType.Bancho_SpectatorLeft, id));
        spectators.forEach(session -> session.writeAndFlush(new IntPacket(PacketType.Bancho_FellowSpectatorLeft, id)));
    }
}
