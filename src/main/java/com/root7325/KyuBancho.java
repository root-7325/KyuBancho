package com.root7325;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.root7325.bancho.service.PingService;
import com.root7325.dao.UserDAO;
import com.root7325.module.AppModule;
import com.root7325.netty.server.BanchoServer;
import com.root7325.utils.ConsoleInputHandler;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

/**
 * @author kate on 02.05.2025
 * <p>
 * Main entry point for this Bancho emulator.
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KyuBancho {
    public static void main(String[] args) {
        log.info("KyuBancho is starting.");

        Injector injector = Guice.createInjector(new AppModule());

        PingService pingService = injector.getInstance(PingService.class);
        BanchoServer banchoServer = injector.getInstance(BanchoServer.class);

        pingService.start();

        Thread serverThread = new Thread(banchoServer::bind);
        serverThread.setDaemon(true);
        serverThread.start();

        ConsoleInputHandler consoleInputHandler = injector.getInstance(ConsoleInputHandler.class);
        consoleInputHandler.start();
    }
}
