package com.root7325;

import com.root7325.bancho.core.ServiceLocator;
import com.root7325.bancho.service.PingService;
import com.root7325.config.Config;
import com.root7325.config.ServerConfig;
import com.root7325.netty.server.BanchoServer;
import com.root7325.netty.server.BanchoServerBootstrap;
import com.root7325.utils.ConsoleInputHandler;
import io.netty.bootstrap.ServerBootstrap;
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

        Config config = Config.getInstance();
        ServerConfig serverConfig = config.getServerConfig();
        ServiceLocator serviceLocator = ServiceLocator.getInstance();

        PingService pingService = new PingService();
        pingService.start();

        ServerBootstrap serverBootstrap = BanchoServerBootstrap.create();
        BanchoServer banchoServer = new BanchoServer(serverBootstrap);

        Thread serverThread = new Thread(() -> banchoServer.bind(serverConfig.getHost(), serverConfig.getPort()));
        serverThread.setDaemon(true);

        serverThread.start();

        ConsoleInputHandler inputHandler = new ConsoleInputHandler(serviceLocator.getUserDAO(), new Scanner(System.in));
        inputHandler.start();
    }
}
