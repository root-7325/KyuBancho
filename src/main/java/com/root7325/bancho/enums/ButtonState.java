package com.root7325.bancho.enums;

/**
 * @author root7325 on 21.04.2024
 */
public enum ButtonState {
    None(0),

    Left1(1),

    Right1(2),

    Left2(4),
    Right2(8);

    ButtonState(int i) {
        this.i = i;
    }

    private final int i;

    public int value() {
        return i;
    }

    public static ButtonState getByValue(int val) {
        for (ButtonState state : ButtonState.values()) {
            if (state.value() == val)
                return state;
        }
        return None;
    }
}
