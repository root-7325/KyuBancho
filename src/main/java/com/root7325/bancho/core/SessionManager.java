package com.root7325.bancho.core;

import com.root7325.bancho.packets.PacketType;
import com.root7325.bancho.packets.impl.UserStatsPacket;
import com.root7325.bancho.packets.impl.generic.IntPacket;
import com.root7325.bancho.service.interfaces.IPacketDispatcherService;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author kate on 03.05.2025
 */
@Slf4j
public class SessionManager {
    private static final SessionManager INSTANCE = new SessionManager();
    private final ConcurrentHashMap<Integer, BanchoSession> activeSessions = new ConcurrentHashMap<>();
    private final IPacketDispatcherService packetDispatcherService = ServiceLocator.getInstance().getPacketDispatcherService();

    private SessionManager() {
    }

    public static SessionManager getInstance() {
        return INSTANCE;
    }

    public Collection<BanchoSession> getSessions() {
        return Collections.unmodifiableCollection(activeSessions.values());
    }

    public int getSessionsAmount() {
        return activeSessions.size();
    }

    public void addSession(BanchoSession session) {
        if (session == null || session.getUser() == null) {
            throw new IllegalArgumentException("Session or user can't be null!");
        }

        int userId = session.getUser().getId();
        Optional<BanchoSession> possibleSession = getSession(userId);

        if (possibleSession.isPresent()) {
            log.warn("{} already online! Removing old session.", userId);
            removeSession(userId);
        }

        activeSessions.put(userId, session);
        packetDispatcherService.broadcast(new UserStatsPacket(session.getUser(), session.getUserStatus()));
    }

    public void removeSession(int userId) {
        BanchoSession session = activeSessions.remove(userId);
        if (session != null) {
            packetDispatcherService.broadcast(new IntPacket(PacketType.Bancho_HandleOsuQuit, userId));
        }
    }

    public void removeSession(BanchoSession session) {
        if (session != null && session.getUser() != null) {
            removeSession(session.getUser().getId());
        }
    }

    public Optional<BanchoSession> getSession(int userId) {
        return Optional.ofNullable(activeSessions.get(userId));
    }

    public Optional<BanchoSession> getSession(String username) {
        return activeSessions.values().stream()
                .filter(session -> username.equals(session.getUser().getUsername()))
                .findFirst();
    }
}