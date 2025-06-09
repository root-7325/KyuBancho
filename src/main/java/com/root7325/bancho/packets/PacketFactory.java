package com.root7325.bancho.packets;

import com.root7325.bancho.packets.impl.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author kate on 02.05.2025
 */
public class PacketFactory {
    private static final Map<PacketType, Supplier<AbstractPacket>> defaultSuppliers = new HashMap<>() {{
        put(PacketType.Osu_Pong, PongPacket::new);
        put(PacketType.Osu_RequestStatusUpdate, RequestUserStatusPacket::new);
        put(PacketType.Osu_SendUserStatus, SendUserStatusPacket::new);
        put(PacketType.Osu_StartSpectating, StartSpectatingPacket::new);
        put(PacketType.Osu_StopSpectating, StopSpectatingPacket::new);
        put(PacketType.Osu_SpectateFrames, SpectateFramesPacket::new);
        put(PacketType.Osu_ChannelJoin, ChannelJoinPacket::new);
        put(PacketType.Osu_SendIrcMessage, ChatMessagePacket::new);
        put(PacketType.Osu_SendIrcMessagePrivate, ChatMessagePacket::new);
    }};

    private final Map<PacketType, Supplier<AbstractPacket>> suppliers;

    public PacketFactory(Map<PacketType, Supplier<AbstractPacket>> suppliers) {
        this.suppliers = Map.copyOf(suppliers);
    }

    public PacketFactory() {
        this(defaultSuppliers);
    }

    public AbstractPacket create(PacketType type) {
        Supplier<AbstractPacket> supplier = suppliers.get(type);
        if (supplier == null) {
            return null;
        }

        AbstractPacket abstractPacket = supplier.get();
        abstractPacket.setPacketType(type);
        return abstractPacket;
    }
}
