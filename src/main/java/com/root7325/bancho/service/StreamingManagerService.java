package com.root7325.bancho.service;

import com.root7325.bancho.core.BanchoSession;
import com.root7325.bancho.service.interfaces.IStreamingManagerService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author root7325 on 25.05.2025
 */
@Slf4j
@Getter
@AllArgsConstructor
public class StreamingManagerService implements IStreamingManagerService {
    private final BanchoSession host;
    private final List<BanchoSession> spectators;

    @Override
    public synchronized void handleNewSpectator(BanchoSession session) {
        session.setSpectatingSubject(host.getUser().getId());
        spectators.add(session);
        log.debug("{} is now spectating {}!", session.getUser().getId(), host.getUser().getId());
    }

    @Override
    public void handleLeftSpectator(BanchoSession session) {
        spectators.remove(session);
    }

    @Override
    public void handleSpectateFrames(Object object) {
        throw new UnsupportedOperationException("handleSpectateFrames is not implemented!");
    }

    @Override
    public void dispose() {
        throw new UnsupportedOperationException("dispose is not implemented!");
    }
}
