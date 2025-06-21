package com.root7325.config;

/**
 * @author root7325 on 21.06.2025
 */

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author root7325 on 17.06.2025
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ServerConfig {
    private String host;
    private int port;
}

