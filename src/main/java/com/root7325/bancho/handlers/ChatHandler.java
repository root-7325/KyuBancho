package com.root7325.bancho.handlers;

import com.root7325.bancho.chat.Channel;
import com.root7325.bancho.chat.ChannelManager;
import com.root7325.bancho.core.BanchoSession;
import com.root7325.bancho.core.ServiceLocator;
import com.root7325.bancho.core.SessionManager;
import com.root7325.bancho.packets.AbstractPacket;
import com.root7325.bancho.packets.PacketType;
import com.root7325.bancho.packets.impl.ChatMessagePacket;
import com.root7325.bancho.service.PacketDispatcherServiceImpl;
import com.root7325.bancho.service.interfaces.IPacketDispatcherService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

/**
 * @author root7325 on 09.06.2025
 */
@Slf4j
public class ChatHandler implements IHandler {
    @Override
    public void handle(AbstractPacket packet, BanchoSession session) {
        ChatMessagePacket messagePacket = (ChatMessagePacket) packet;
        messagePacket.setPacketType(PacketType.Bancho_SendIrcMessage);
        messagePacket.setSender(session.getUser().getUsername());

        ChannelManager manager = ChannelManager.getInstance();
        if (messagePacket.isPrivate()) {
            log.debug("Handling PM");
            handlePrivateMessage(messagePacket);
        } else {
            log.debug("Handling chat message");
            handleChatMessage(manager, messagePacket);
        }
    }

    private void handlePrivateMessage(ChatMessagePacket packet) {
        SessionManager sessionManager = SessionManager.getInstance();

        Optional<BanchoSession> optionalTarget = sessionManager.getSession(packet.getTarget());
        optionalTarget.ifPresentOrElse(
                target -> target.writeAndFlush(packet),
                () -> log.debug("{} is unavailable", packet.getTarget())
        );
    }

    private void handleChatMessage(ChannelManager channelManager, ChatMessagePacket packet) {
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
