package com.root7325.bancho.service;

import com.google.inject.Inject;
import com.root7325.bancho.core.ISessionManager;
import com.root7325.bancho.packet.PacketType;
import com.root7325.bancho.packet.impl.generic.EmptyPacket;
import com.root7325.utils.Constants;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Service that handles client ping/pong mechanism.
 * Sends ping packets to all connected clients and removes inactive ones.
 *
 * @author kate on 03.05.2025
 */
@Slf4j
public class PingService {
    private final ScheduledExecutorService scheduler;
    private final ISessionManager sessionManager;

    @Inject
    public PingService(ISessionManager sessionManager) {
        this.sessionManager = sessionManager;
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
    }

    public void start() {
        scheduler.scheduleAtFixedRate(
                this::ping,
                0,
                Constants.PING_TIMEOUT,
                TimeUnit.SECONDS
        );
        log.info("PingService started.");
    }

    private void ping() {
        log.trace("Ping was called.");
        sessionManager.getSessions().forEach(session -> {
            if (System.currentTimeMillis() - session.getLastPongTime() > 15_000) {
                session.getChannel().close();
                sessionManager.removeSession(session);
            }

            session.writeAndFlush(new EmptyPacket(PacketType.Bancho_Ping));
        });
    }
}
