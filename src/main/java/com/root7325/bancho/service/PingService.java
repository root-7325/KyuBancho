package com.root7325.bancho.service;

import com.root7325.bancho.core.SessionManager;
import com.root7325.bancho.packet.PacketType;
import com.root7325.bancho.packet.impl.generic.EmptyPacket;
import com.root7325.utils.Constants;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author kate on 03.05.2025
 * <p>
 * Service that handles client ping/pong mechanism.
 * Sends ping packets to all connected clients and removes inactive ones.
 */
@Slf4j
public class PingService {
    private final ScheduledExecutorService scheduler;

    public PingService() {
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
        SessionManager instance = SessionManager.getInstance();
        instance.getSessions().forEach(session -> {
            if (System.currentTimeMillis() - session.getLastPongTime() > 15_000) {
                instance.removeSession(session);
            }

            session.write(new EmptyPacket(PacketType.Bancho_Ping));
            session.flush();
        });
    }
}
