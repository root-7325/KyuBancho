package com.root7325.bancho.handler;

import com.google.inject.Inject;
import com.root7325.bancho.chat.Channel;
import com.root7325.bancho.chat.ChannelManager;
import com.root7325.bancho.core.BanchoSession;
import com.root7325.bancho.core.ISessionManager;
import com.root7325.bancho.packet.AbstractPacket;
import com.root7325.bancho.packet.PacketType;
import com.root7325.bancho.packet.impl.ChatMessagePacket;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

/**
 * @author root7325 on 09.06.2025
 */
@Slf4j
@AllArgsConstructor(onConstructor = @__({@Inject}))
public class ChatHandler implements IHandler {
    private final ISessionManager sessionManager;
    private final ChannelManager channelManager;

    @Override
    public void handle(AbstractPacket packet, BanchoSession session) {
        ChatMessagePacket messagePacket = (ChatMessagePacket) packet;
        messagePacket.setPacketType(PacketType.Bancho_SendIrcMessage);
        messagePacket.setSender(session.getUser().getUsername());

        if (messagePacket.isPrivate()) {
            log.debug("Handling PM");
            handlePrivateMessage(messagePacket);
        } else {
            log.debug("Handling chat message");
            handleChatMessage(messagePacket);
        }
    }

    private void handlePrivateMessage(ChatMessagePacket packet) {
        Optional<BanchoSession> optionalTarget = sessionManager.getSession(packet.getTarget());
        optionalTarget.ifPresentOrElse(
                target -> target.writeAndFlush(packet),
                () -> log.debug("{} is unavailable", packet.getTarget())
        );
    }

    private void handleChatMessage(ChatMessagePacket packet) {
        Optional<Channel> optionalChannel = channelManager.getChannel(packet.getTarget());

        optionalChannel.ifPresent(channel -> {
            List<BanchoSession> sessionList = channel.getParticipants();
            sessionList.forEach(session -> {
                if (!session.getUser().getUsername().equals(packet.getSender())) {
                    session.writeAndFlush(packet);
                }
            });
        });
    }
}
