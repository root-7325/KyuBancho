package com.root7325.module;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Main application module that installs all other required modules.
 *
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

    @Provides
    private ExecutorService provideExecutorService() {
        return Executors.newCachedThreadPool();
    }

    @Provides
    @Singleton
    private ScheduledExecutorService provideScheduledExecutorService() {
        return Executors.newSingleThreadScheduledExecutor();
    }
}
