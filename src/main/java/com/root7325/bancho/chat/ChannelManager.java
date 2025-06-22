package com.root7325.bancho.chat;

import com.google.inject.Inject;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author root7325 on 09.06.2025
 */
public class ChannelManager {
    private final Map<String, Channel> channels = new ConcurrentHashMap<>();

    @Inject
    public ChannelManager() {
        this.channels.put("#osu", new Channel("#osu"));
    }

    public Channel createChannel(String name) {
        Channel channel = new Channel(name);
        channels.put(name, channel);
        return channel;
    }

    public Set<String> getChannelsName() {
        return channels.keySet();
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