package com.root7325.bancho.core;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.root7325.bancho.handler.*;
import com.root7325.bancho.packet.AbstractPacket;
import com.root7325.bancho.packet.PacketType;
import com.root7325.netty.codec.LoginDataDecoder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author kate on 02.05.2025
 * <p>
 * Routes incoming packets to appropriate handlers.
 * Manages packet handling logic for different packet types.
 */
@Slf4j
@AllArgsConstructor(onConstructor = @__({@Inject}))
public class PacketRouter {
    private final Map<PacketType, IHandler> handlers;
    private final LoginHandler loginHandler;

    public void handle(AbstractPacket packet, BanchoSession session) {
        IHandler handler = handlers.get(packet.getPacketType());
        if (handler != null) {
            handler.handle(packet, session);
            log.debug("Packet handled by {}", handler.getClass().getSimpleName());
        } else {
            log.warn("No handler for {}", packet.getPacketType());
        }
    }

    public void handleLogin(LoginDataDecoder.LoginData data, BanchoSession banchoSession) {
        loginHandler.handle(data, banchoSession);
    }
}