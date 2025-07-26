package com.root7325.bancho.core;

import com.root7325.bancho.packet.AbstractPacket;
import com.root7325.bancho.service.StreamingManagerService;
import com.root7325.bancho.structure.UserStatus;
import com.root7325.entity.User;
import io.netty.channel.Channel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author kate on 02.05.2025
 */
@Slf4j
@Getter
@Setter
public class BanchoSession {
    private Channel channel;
    private final Queue<AbstractPacket> packetQueue = new ConcurrentLinkedQueue<>();

    private User user = new User();
    private UserStatus userStatus = new UserStatus();

    private long lastPongTime = System.currentTimeMillis();

    private int spectatingSubject;
    private StreamingManagerService streamingManagerService = new StreamingManagerService(this, new ArrayList<>());

    public void write(AbstractPacket... packets) {
        Collections.addAll(packetQueue, packets);
    }

    public void flush() {
        if (!channel.isActive()) {
            packetQueue.clear();
            return;
        }

        if (!channel.eventLoop().inEventLoop()) {
            channel.eventLoop().execute(this::flush);
            return;
        }

        try {
            while (!packetQueue.isEmpty()) {
                AbstractPacket packet = packetQueue.poll();
                if (packet != null) {
                    channel.write(packet);
                }
            }
            channel.flush();
        } catch (Exception ex) {
            log.error("Failed to flush packet queue.", ex);
            packetQueue.clear();
        }
    }

    public void writeAndFlush(AbstractPacket... packets) {
        write(packets);
        channel.eventLoop().execute(this::flush);
    }
}
