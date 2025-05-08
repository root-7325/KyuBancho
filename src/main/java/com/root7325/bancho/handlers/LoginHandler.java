package com.root7325.bancho.handlers;

import com.root7325.bancho.core.BanchoSession;
import com.root7325.bancho.core.SessionManager;
import com.root7325.bancho.enums.Permissions;
import com.root7325.bancho.packets.PacketType;
import com.root7325.bancho.packets.impl.UserStatsPacket;
import com.root7325.bancho.packets.impl.generic.IntPacket;
import com.root7325.bancho.packets.impl.generic.StringPacket;
import com.root7325.netty.codec.LoginDataDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author kate on 02.05.2025
 */
@Slf4j
public class LoginHandler {

    public void handle(LoginDataDecoder.LoginData data, BanchoSession session) {
        log.debug("{} issued login!", data.getUsername());
        SessionManager instance = SessionManager.getInstance();

        session.getUser().setId(ThreadLocalRandom.current().nextInt(0, Short.MAX_VALUE)); // !!
        session.getUser().setUsername(data.getUsername());

        session.write(
          new IntPacket(PacketType.Bancho_ProtocolNegotiation, 12),
                new IntPacket(PacketType.Bancho_LoginReply, session.getUser().getId()),
                new IntPacket(PacketType.Bancho_LoginPermissions, Permissions.Subscriber.getValue()),
                new StringPacket(PacketType.Bancho_Announce, "KyuBancho - welcome!")
        );
        instance.getSessions().forEach(banchoSession -> {
            session.write(new UserStatsPacket(banchoSession.getUser(), banchoSession.getUserStatus()));
        });

        session.flush();
        instance.addSession(session);
        log.debug("{} is logged in!", data.getUsername());
    }
}
