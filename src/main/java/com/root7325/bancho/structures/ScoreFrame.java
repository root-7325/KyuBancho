package com.root7325.bancho.structures;

import io.netty.buffer.ByteBuf;

/**
 * @author root7325 on 21.04.2024
 */
public class ScoreFrame {
    public byte id;
    public int time;
    public short count100;
    public short count300;
    public short count50;
    public short countGeki;
    public short countKatu;
    public short countMiss;
    public short currentCombo;
    public short maxCombo;
    public int currentHp;
    public boolean pass;
    public boolean perfect;
    public int totalScore;

    public ScoreFrame(ByteBuf buf) {
        this.time = buf.readIntLE();
        this.id = buf.readByte();
        this.count300 = buf.readShortLE();
        this.count100 = buf.readShortLE();
        this.count50 = buf.readShortLE();
        this.countGeki = buf.readShortLE();
        this.countKatu = buf.readShortLE();
        this.countMiss = buf.readShortLE();
        this.totalScore = buf.readIntLE();
        this.maxCombo = buf.readShortLE();
        this.currentCombo = buf.readShortLE();
        this.perfect = buf.readBoolean();
        this.currentHp = buf.readByte();
        if (currentHp == 254) {
            currentHp = 0;
            pass = false;
            return;
        }
        pass = true;
    }

    public void writeToStream(ByteBuf buf) {
        buf.writeIntLE(time);
        buf.writeShortLE(count300);
        buf.writeShortLE(count100);
        buf.writeShortLE(count50);
        buf.writeShortLE(countGeki);
        buf.writeShortLE(countKatu);
        buf.writeShortLE(countMiss);
        buf.writeIntLE(totalScore);
        buf.writeShortLE(maxCombo);
        buf.writeShortLE(currentCombo);
        buf.writeBoolean(perfect);
        buf.writeByte((this.pass ? this.currentHp : 254));
    }
}
