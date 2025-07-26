package com.root7325.bancho.structure;

import com.root7325.bancho.enums.BitFlagEnum;
import com.root7325.bancho.enums.ButtonState;
import io.netty.buffer.ByteBuf;

import java.util.EnumSet;

/**
 * @author root7325 on 21.04.2024
 */
public class ReplayFrame {
    public float mouseX;
    public float mouseY;

    public EnumSet<ButtonState> buttonStates;
    public byte flags;
    public int time;

    public ReplayFrame(ByteBuf buf) {
        this.flags = buf.readByte();
        this.buttonStates = BitFlagEnum.decodeFlags(flags, ButtonState.class);
        int b = buf.readByte();
        if (b > 0) {
            this.buttonStates = BitFlagEnum.decodeFlags(ButtonState.Right1.getBitMask(), ButtonState.class);
        }
        this.mouseX = buf.readFloatLE();
        this.mouseY = buf.readFloatLE();
        this.time = buf.readIntLE();
    }

    public void writeToStream(ByteBuf buf) {
        buf.writeByte(flags);
        buf.writeByte(0);
        buf.writeFloatLE(mouseX);
        buf.writeFloatLE(mouseY);
        buf.writeIntLE(time);
    }
}
