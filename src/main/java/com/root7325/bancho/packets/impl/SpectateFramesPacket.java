package com.root7325.bancho.packets.impl;

import com.root7325.bancho.enums.ReplayAction;
import com.root7325.bancho.packets.AbstractPacket;
import com.root7325.bancho.packets.PacketType;
import com.root7325.bancho.structures.ReplayFrame;
import com.root7325.bancho.structures.ScoreFrame;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author root7325 on 21.04.2024
 */
@Slf4j
@Getter
@NoArgsConstructor
public class SpectateFramesPacket extends AbstractPacket {
    private PacketType type;
    private List<ReplayFrame> replayFrameList;
    private ScoreFrame scoreFrame;
    private ReplayAction replayAction;

    public SpectateFramesPacket(PacketType type) {
        this.type = type;
    }

    @Override
    public void readFromStream(ByteBuf in) {
        this.replayFrameList = new ArrayList<>();
        int num = in.readShortLE();
        log.debug("{}", num);

        for (int i = 0; i < num; i++) {
            replayFrameList.add(new ReplayFrame(in));
        }
        this.replayAction = ReplayAction.values()[in.readByte()];
        this.scoreFrame = new ScoreFrame(in);
    }

    @Override
    public void writeToStream(ByteBuf out) {
        out.writeShortLE(replayFrameList.size());

        for(ReplayFrame bReplayFrame : replayFrameList) {
            bReplayFrame.writeToStream(out);
        }
        out.writeByte(replayAction.ordinal());
        scoreFrame.writeToStream(out);
    }
}
