package com.root7325.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.InputStream;

/**
 * @author root7325 on 10.02.2025
 */
/**
 * @author root7325 on 21.06.2025
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Config {
    private ServerConfig serverConfig;
}
