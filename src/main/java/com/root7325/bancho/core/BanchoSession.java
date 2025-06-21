package com.root7325.bancho.core;

import com.root7325.bancho.packet.AbstractPacket;
import com.root7325.bancho.service.StreamingManagerService;
import com.root7325.bancho.structure.UserStatus;
import com.root7325.entity.User;
import io.netty.channel.Channel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

/**
 * @author kate on 02.05.2025
 */
@Slf4j
@Getter
@Setter
public class BanchoSession {
    private Channel channel;
    private User user = new User();
    private UserStatus userStatus = new UserStatus();
    private long lastPongTime = System.currentTimeMillis();
    private int spectatingSubject;
    private StreamingManagerService streamingManagerService = new StreamingManagerService(this, new ArrayList<>());

    public void write(AbstractPacket... packets) {
        for (AbstractPacket packet : packets) {
            channel.write(packet);
        }
    }

    public void writeAndFlush(AbstractPacket... packets) {
        write(packets);
        flush();
    }

    public void flush() {
        channel.flush();
    }
}
