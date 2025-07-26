package com.root7325.bancho.service.interfaces;

import com.root7325.bancho.core.BanchoSession;
import com.root7325.bancho.packet.impl.SpectateFramesPacket;

/**
 * @author root7325 on 25.05.2025
 */
public interface IStreamingManagerService {
    void handleNewSpectator(BanchoSession session);
    void handleLeftSpectator(BanchoSession session);
    void handleSpectateFrames(SpectateFramesPacket spectateFramesPacket);
    void dispose();
}
