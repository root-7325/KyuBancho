package com.root7325.bancho.handler;

import com.google.inject.Inject;
import com.root7325.bancho.chat.ChannelManager;
import com.root7325.bancho.core.BanchoSession;
import com.root7325.bancho.core.ISessionManager;
import com.root7325.bancho.packet.PacketType;
import com.root7325.bancho.packet.impl.UserStatsPacket;
import com.root7325.bancho.packet.impl.generic.IntPacket;
import com.root7325.bancho.packet.impl.generic.StringPacket;
import com.root7325.dao.UserDAO;
import com.root7325.entity.User;
import com.root7325.netty.codec.LoginDataDecoder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author kate on 02.05.2025
 */
@Slf4j
@AllArgsConstructor(onConstructor = @__({@Inject}))
public class LoginHandler {
    private final ISessionManager sessionManager;
    private final UserDAO userDAO;
    private final ChannelManager channelManager;

    public void handle(LoginDataDecoder.LoginData loginData, BanchoSession session) {
        log.info("{} issued login!", loginData.getUsername());

        User user = loadUser(loginData);

        if (user == null) {
            session.write(new IntPacket(PacketType.Bancho_LoginReply, -1));
            session.flush();
            log.info("Incorrect login attempt for {}!", loginData.getUsername());
        } else {
            processLogin(session, user);
        }
    }

    private User loadUser(LoginDataDecoder.LoginData loginData) {
        return userDAO.getUser(loginData.getUsername(), loginData.getPasswordHash());
    }

    private void processLogin(BanchoSession session, User user) {
        session.setUser(user);
        session.write(
                new IntPacket(PacketType.Bancho_ProtocolNegotiation, 12),
                new IntPacket(PacketType.Bancho_LoginReply, user.getId()),
                new IntPacket(PacketType.Bancho_LoginPermissions, user.getPermissions().getValue()),
                new StringPacket(PacketType.Bancho_Announce, "KyuBancho - welcome!")
        );
        processChannels(session);

        sessionManager.addSession(session);
        sessionManager.getSessions().forEach(banchoSession ->
            session.write(new UserStatsPacket(banchoSession.getUser(), banchoSession.getUserStatus()))
        );

        session.flush();
        log.info("{} is logged in!", user.getUsername());
    }

    private void processChannels(BanchoSession session) {
        channelManager.getChannel("#osu").get().newParticipant(session);

        channelManager.getChannelsName().forEach(channel -> {
            session.write(new StringPacket(PacketType.Bancho_ChannelAvailable, channel));
        });

    }
}
