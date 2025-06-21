package com.root7325.bancho.core;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.root7325.bancho.handler.*;
import com.root7325.bancho.packet.AbstractPacket;
import com.root7325.bancho.packet.PacketType;
import com.root7325.netty.codec.LoginDataDecoder;
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
public class PacketRouter {
    private final Map<PacketType, Class<? extends IHandler>> handlers;
    private final Injector injector;

    @Inject
    public PacketRouter(Injector injector) {
        this.injector = injector;
        this.handlers = new ConcurrentHashMap<>();

        registerDefaults();
    }

    private void registerDefaults() {
        handlers.put(PacketType.Osu_Pong, PongHandler.class);
        handlers.put(PacketType.Osu_RequestStatusUpdate, RequestStatusUpdateHandler.class);
        handlers.put(PacketType.Osu_SendUserStatus, SendUserStatusHandler.class);
        handlers.put(PacketType.Osu_StartSpectating, StartSpectatingHandler.class);
        handlers.put(PacketType.Osu_StopSpectating, StopSpectatingHandler.class);
        handlers.put(PacketType.Osu_SpectateFrames, SpectateFramesHandler.class);
        handlers.put(PacketType.Osu_ChannelJoin, ChannelJoinHandler.class);
        handlers.put(PacketType.Osu_SendIrcMessage, ChatHandler.class);
        handlers.put(PacketType.Osu_SendIrcMessagePrivate, ChatHandler.class);
    }

    public void handle(AbstractPacket packet, BanchoSession session) {
        Class<? extends IHandler> handlerClass = handlers.get(packet.getPacketType());
        if (handlerClass != null) {
            IHandler handler = injector.getInstance(handlerClass);
            handler.handle(packet, session);
            log.debug("Packet handled by {}", handler.getClass().getSimpleName());
        } else {
            log.warn("No handler for {}", packet.getPacketType());
        }
    }

    public void handleLogin(LoginDataDecoder.LoginData data, BanchoSession banchoSession) {
        LoginHandler handler = injector.getInstance(LoginHandler.class);
        handler.handle(data, banchoSession);
    }
}