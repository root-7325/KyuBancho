package com.root7325.module;

import com.google.inject.AbstractModule;

/**
 * @author root7325 on 22.06.2025
 */
public class AppModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new ConfigModule());
        install(new DatabaseModule());
        install(new ServiceModule());
        install(new NettyModule());
        install(new ChatModule());
    }
}
