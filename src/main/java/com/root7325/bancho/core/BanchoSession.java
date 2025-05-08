package com.root7325.bancho.core;

import com.root7325.KyuBancho;
import com.root7325.bancho.packets.AbstractPacket;
import com.root7325.bancho.structures.UserStatus;
import com.root7325.entities.User;
import io.netty.channel.Channel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author kate on 02.05.2025
 */
@Slf4j
@Getter
@RequiredArgsConstructor
public class BanchoSession {
    private final Channel channel;
    @Setter
    private User user = new User();
    @Setter
    private UserStatus userStatus = new UserStatus();
    @Setter
    private long lastPongTime = System.currentTimeMillis();

    public void write(AbstractPacket... packets) {
        for (AbstractPacket packet : packets) {
            channel.write(packet);
        }
    }

    public void flush() {
        channel.flush();
    }
}
