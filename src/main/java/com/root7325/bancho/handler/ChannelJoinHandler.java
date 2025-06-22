package com.root7325.bancho.handler;

import com.google.inject.Inject;
import com.root7325.bancho.chat.Channel;
import com.root7325.bancho.chat.ChannelManager;
import com.root7325.bancho.core.BanchoSession;
import com.root7325.bancho.packet.AbstractPacket;
import com.root7325.bancho.packet.impl.ChannelJoinPacket;
import lombok.AllArgsConstructor;

import java.util.Optional;

/**
 * @author root7325 on 09.06.2025
 */
@AllArgsConstructor(onConstructor = @__({@Inject}))
public class ChannelJoinHandler implements IHandler {
    private final ChannelManager channelManager;

    @Override
    public void handle(AbstractPacket packet, BanchoSession session) {
        String channelName = ((ChannelJoinPacket) packet).getS();

        Optional<Channel> optionalChannel = channelManager.getChannel(channelName);

        optionalChannel.ifPresent(channel -> channel.newParticipant(session));
    }
}
