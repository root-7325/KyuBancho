package com.root7325.module;

import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.root7325.config.Config;
import com.root7325.config.DatabaseConfig;
import com.root7325.config.ServerConfig;
import com.typesafe.config.ConfigBeanFactory;
import com.typesafe.config.ConfigFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * This module provides configuration bindings.
 *
 * @author root7325 on 21.06.2025
 */
public class ConfigModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Config.class).toProvider(ConfigProvider.class).asEagerSingleton();
    }

    @Provides
    private ServerConfig provideServerConfig(Config config) {
        return config.getServerConfig();
    }

    @Provides
    private DatabaseConfig provideDatabaseConfig(Config config) {
        return config.getDatabaseConfig();
    }
}

@Slf4j
class ConfigProvider implements Provider<Config> {
    private static final String HOCON_FILE = "application.conf";

    @Override
    public Config get() {
        log.trace("Trying to load server configuration.");

        com.typesafe.config.Config defaultConfig = ConfigFactory.load(HOCON_FILE);
        com.typesafe.config.Config externalConfig = ConfigFactory.parseFile(new File(HOCON_FILE));

        return ConfigBeanFactory.create(
                externalConfig.withFallback(defaultConfig).resolve(),
                Config.class
        );
    }
}