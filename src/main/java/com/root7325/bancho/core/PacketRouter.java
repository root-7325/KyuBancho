package com.root7325.bancho.core;

import com.root7325.bancho.handler.*;
import com.root7325.bancho.packet.AbstractPacket;
import com.root7325.bancho.packet.PacketType;
import com.root7325.netty.codec.LoginDataDecoder;
import lombok.extern.slf4j.Slf4j;

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
    private final Map<PacketType, IHandler> handlers;
    private final LoginHandler loginHandler;

    public PacketRouter() {
        this.handlers = new ConcurrentHashMap<>();
        this.loginHandler = new LoginHandler();

        registerDefaults();
    }

    private void registerDefaults() {
        handlers.put(PacketType.Osu_Pong, new PongHandler());
        handlers.put(PacketType.Osu_RequestStatusUpdate, new RequestStatusUpdateHandler());
        handlers.put(PacketType.Osu_SendUserStatus, new SendUserStatusHandler());
        handlers.put(PacketType.Osu_StartSpectating, new StartSpectatingHandler());
        handlers.put(PacketType.Osu_StopSpectating, new StopSpectatingHandler());
        handlers.put(PacketType.Osu_SpectateFrames, new SpectateFramesHandler());
        handlers.put(PacketType.Osu_ChannelJoin, new ChannelJoinHandler());
        handlers.put(PacketType.Osu_SendIrcMessage, new ChatHandler());
        handlers.put(PacketType.Osu_SendIrcMessagePrivate, new ChatHandler());
    }

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