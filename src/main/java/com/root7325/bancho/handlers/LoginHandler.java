package com.root7325.bancho.handlers;

import com.root7325.bancho.core.BanchoSession;
import com.root7325.bancho.core.ServiceLocator;
import com.root7325.bancho.core.SessionManager;
import com.root7325.bancho.enums.Permissions;
import com.root7325.bancho.packets.PacketType;
import com.root7325.bancho.packets.impl.UserStatsPacket;
import com.root7325.bancho.packets.impl.generic.IntPacket;
import com.root7325.bancho.packets.impl.generic.StringPacket;
import com.root7325.dao.UserDAOImpl;
import com.root7325.entities.User;
import com.root7325.netty.codec.LoginDataDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author kate on 02.05.2025
 */
@Slf4j
public class LoginHandler {

    public void handle(LoginDataDecoder.LoginData loginData, BanchoSession session) {
        log.debug("{} issued login!", loginData.getUsername());
        SessionManager instance = SessionManager.getInstance();

        User user = loadUser(loginData);

        if (user == null) {
            session.write(new IntPacket(PacketType.Bancho_LoginReply, -1));
            session.flush();
            log.debug("Incorrect login attempt for {}!", loginData.getUsername());
        } else {
            processLogin(instance, session, user);
        }
    }

    private User loadUser(LoginDataDecoder.LoginData loginData) {
        UserDAOImpl userDAO = ServiceLocator.getInstance().getUserDAO();
        return userDAO.getUser(loginData.getUsername(), loginData.getPasswordHash());
    }

    private void processLogin(SessionManager sessionManager, BanchoSession session, User user) {
        session.setUser(user);
        session.write(
                new IntPacket(PacketType.Bancho_ProtocolNegotiation, 12),
                new IntPacket(PacketType.Bancho_LoginReply, user.getId()),
                new IntPacket(PacketType.Bancho_LoginPermissions, user.getPermissions().getValue()),
                new StringPacket(PacketType.Bancho_Announce, "KyuBancho - welcome!")
        );
        sessionManager.getSessions().forEach(banchoSession -> {
            session.write(new UserStatsPacket(user, banchoSession.getUserStatus()));
        });

        session.flush();
        sessionManager.addSession(session);
        log.debug("{} is logged in!", user.getUsername());
    }
}
