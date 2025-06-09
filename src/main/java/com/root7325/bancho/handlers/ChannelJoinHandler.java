package com.root7325.bancho.handlers;

import com.root7325.bancho.chat.Channel;
import com.root7325.bancho.chat.ChannelManager;
import com.root7325.bancho.core.BanchoSession;
import com.root7325.bancho.packets.AbstractPacket;
import com.root7325.bancho.packets.impl.ChannelJoinPacket;

import java.util.Optional;

/**
 * @author root7325 on 09.06.2025
 */
public class ChannelJoinHandler implements IHandler {
    @Override
    public void handle(AbstractPacket packet, BanchoSession session) {
        String channelName = ((ChannelJoinPacket) packet).getS();

        ChannelManager manager = ChannelManager.getInstance();
        Optional<Channel> optionalChannel = manager.getChannel(channelName);

        optionalChannel.ifPresent(channel -> channel.newParticipant(session));
    }
}
