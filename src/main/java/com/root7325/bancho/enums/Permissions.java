package com.root7325.bancho.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author kate on 03.05.2025
 */
@Getter
@RequiredArgsConstructor
public enum Permissions {
    None(2),
    Normal(1),
    Bat(2),
    Subscriber(4);

    private final int value;
}
