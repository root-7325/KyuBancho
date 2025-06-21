package com.root7325.bancho.core;

import java.util.Collection;
import java.util.Optional;

/**
 * @author root7325 on 22.06.2025
 */
public interface ISessionManager {
    Collection<BanchoSession> getSessions();
    int getSessionsAmount();
    void addSession(BanchoSession session);
    void removeSession(int userId);
    void removeSession(BanchoSession session);
    Optional<BanchoSession> getSession(int userId);
    Optional<BanchoSession> getSession(String username);
}
