package com.root7325.bancho.packet.impl;

import com.root7325.bancho.enums.ReplayAction;
import com.root7325.bancho.packet.AbstractPacket;
import com.root7325.bancho.packet.PacketType;
import com.root7325.bancho.structure.ReplayFrame;
import com.root7325.bancho.structure.ScoreFrame;
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

    @Override
    public void readFromStream(ByteBuf in) {
        this.replayFrameList = new ArrayList<>();
        int num = in.readShortLE();

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
