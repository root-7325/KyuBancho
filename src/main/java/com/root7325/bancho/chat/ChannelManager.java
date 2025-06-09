package com.root7325.bancho.chat;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author root7325 on 09.06.2025
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChannelManager {
    private static final ChannelManager INSTANCE = new ChannelManager();

    private final Map<String, Channel> channels = new ConcurrentHashMap<>();

    public static ChannelManager getInstance() {
        return INSTANCE;
    }

    public Channel createChannel(String name) {
        Channel channel = new Channel(name);
        channels.put(name, channel);
        return channel;
    }

    public boolean removeChannel(String name) {
        return channels.remove(name) != null;
    }

    public Optional<Channel> getChannel(String name) {
        return Optional.ofNullable(channels.get(name));
    }

    public boolean channelExists(String name) {
        return channels.containsKey(name);
    }
}