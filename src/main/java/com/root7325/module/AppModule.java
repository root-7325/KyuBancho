package com.root7325.module;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import java.util.Scanner;

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
        install(new HandlerModule());
    }

    @Provides
    private Scanner provideScanner() {
        return new Scanner(System.in);
    }
}
