package com.root7325.bancho.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author root7325 on 21.04.2024
 */
@Slf4j
@Getter
@AllArgsConstructor
public enum ButtonState implements BitFlagEnum {
    None(0),

    Left1(1),

    Right1(2),

    Left2(4),
    Right2(8);

    private final int bitMask;
}
