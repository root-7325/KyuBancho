package com.root7325.bancho.structure;

import com.root7325.bancho.enums.ButtonState;
import io.netty.buffer.ByteBuf;

/**
 * @author root7325 on 21.04.2024
 */
public class ReplayFrame {
    public float mouseX;
    public float mouseY;

    public boolean mouseLeft;
    public boolean mouseRight;
    public boolean mouseLeft1;
    public boolean mouseRight1;
    public boolean mouseLeft2;
    public boolean mouseRight2;
    public ButtonState buttonState;
    public int time;

    private void setButtonStates(ButtonState buttonState) {
        this.mouseLeft |= ((buttonState.value() & (ButtonState.Left1.value() | ButtonState.Left2.value())) > ButtonState.None.value());
        this.mouseLeft1 |= ((buttonState.value() & ButtonState.Left1.value()) > ButtonState.None.value());
        this.mouseLeft2 |= ((buttonState.value() & ButtonState.Left2.value()) > ButtonState.None.value());
        this.mouseRight |= ((buttonState.value() & (ButtonState.Right1.value() | ButtonState.Right2.value())) > ButtonState.None.value());
        this.mouseRight1 |= ((buttonState.value() & ButtonState.Right1.value()) > ButtonState.None.value());
        this.mouseRight2 |= ((buttonState.value() & ButtonState.Right2.value()) > ButtonState.None.value());
    }

    public ReplayFrame(ByteBuf buf) {
        this.buttonState = ButtonState.getByValue(buf.readByte() / 5);
        setButtonStates(buttonState);
        int b = buf.readByte();
        if (b > 0) {
            setButtonStates(ButtonState.Right1);
        }
        this.mouseX = buf.readFloatLE();
        this.mouseY = buf.readFloatLE();
        this.time = buf.readIntLE();
    }

    public void writeToStream(ByteBuf buf) {
        buf.writeByte(buttonState.value());
        buf.writeByte(0);
        buf.writeFloatLE(mouseX);
        buf.writeFloatLE(mouseY);
        buf.writeIntLE(time);
    }
}
