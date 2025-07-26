package com.root7325.config;

import lombok.Getter;
import lombok.Setter;

/**
 * Main configuration container class that holds all server configuration settings.
 *
 * @author root7325 on 10.02.2025
 */
@Getter
@Setter
public class Config {
    private ServerConfig serverConfig;
    private DatabaseConfig databaseConfig;
}
