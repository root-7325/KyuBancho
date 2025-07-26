package com.root7325.module;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.MapBinder;
import com.root7325.bancho.handler.*;
import com.root7325.bancho.packet.PacketType;

/**
 * This module binds message handler implementations.
 *
 * @author root7325 on 22.06.2025
 */
public class HandlerModule extends AbstractModule {
    @Override
    protected void configure() {
        MapBinder<PacketType, IHandler> mapBinder = MapBinder.newMapBinder(
                binder(), PacketType.class, IHandler.class
        );

        mapBinder.addBinding(PacketType.Osu_Pong).to(PongHandler.class);
        mapBinder.addBinding(PacketType.Osu_RequestStatusUpdate).to(RequestStatusUpdateHandler.class);
        mapBinder.addBinding(PacketType.Osu_SendUserStatus).to(SendUserStatusHandler.class);
        mapBinder.addBinding(PacketType.Osu_StartSpectating).to(StartSpectatingHandler.class);
        mapBinder.addBinding(PacketType.Osu_StopSpectating).to(StopSpectatingHandler.class);
        mapBinder.addBinding(PacketType.Osu_SpectateFrames).to(SpectateFramesHandler.class);
        mapBinder.addBinding(PacketType.Osu_ChannelJoin).to(ChannelJoinHandler.class);
        mapBinder.addBinding(PacketType.Osu_SendIrcMessage).to(ChatHandler.class);
        mapBinder.addBinding(PacketType.Osu_SendIrcMessagePrivate).to(ChatHandler.class);
    }
}
