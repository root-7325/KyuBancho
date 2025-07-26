package com.root7325.bancho.core;

import com.google.inject.Inject;
import com.root7325.bancho.handler.*;
import com.root7325.bancho.packet.AbstractPacket;
import com.root7325.bancho.packet.PacketType;
import com.root7325.netty.codec.LoginDataDecoder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

/**
 * Routes incoming packets to appropriate handlers.
 * Manages packet handling logic for different packet types.
 *
 * @author kate on 02.05.2025
 */
@Slf4j
@AllArgsConstructor(onConstructor = @__({@Inject}))
public class PacketRouter {
    private final Map<PacketType, IHandler> handlers;
    private final LoginHandler loginHandler;
    private final ExecutorService executorService;

    public void handle(AbstractPacket packet, BanchoSession session) {
        IHandler handler = handlers.get(packet.getPacketType());
        if (handler != null) {
            CompletableFuture.runAsync(() -> {
                        handler.handle(packet, session);
                        log.debug("Packet handled by {}", handler.getClass().getSimpleName());
                    }, executorService)
                    .exceptionally(ex -> {
                        log.error("Error handling message {} by {}", packet, handler.getClass().getSimpleName());
                        return null;
                    });
        } else {
            log.warn("No handler for {}", packet.getPacketType());
        }
    }

    public void handleLogin(LoginDataDecoder.LoginData data, BanchoSession banchoSession) {
        loginHandler.handle(data, banchoSession);
    }
}