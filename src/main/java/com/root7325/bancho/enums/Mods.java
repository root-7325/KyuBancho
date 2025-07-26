package com.root7325.bancho.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author kate on 03.05.2025
 */
@Getter
@AllArgsConstructor
public enum Mods implements BitFlagEnum {
    None(0),

    NoFail(1),

    Easy(2),

    NoVideo(4),

    Hidden(8),

    HardRock(16),

    SuddenDeath(32),

    DoubleTime(64),

    Relax(128),

    HalfTime(256),

    Flashlight(1024),

    Autoplay(2048),

    SpunOut(4096);

    private final int bitMask;
}