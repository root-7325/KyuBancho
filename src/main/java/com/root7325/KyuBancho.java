package com.root7325;

import com.root7325.bancho.core.ServiceLocator;
import com.root7325.bancho.core.SessionManager;
import com.root7325.bancho.enums.Permissions;
import com.root7325.bancho.service.PingService;
import com.root7325.dao.UserDAOImpl;
import com.root7325.entities.User;
import com.root7325.netty.server.BanchoServer;
import com.root7325.netty.server.BanchoServerBootstrap;
import com.root7325.utils.ConsoleInputHandler;
import com.root7325.utils.HibernateUtil;
import io.netty.bootstrap.ServerBootstrap;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;

import java.util.Scanner;

/**
 * @author kate on 02.05.2025
 * <p>
 * Main entry point for this Bancho emulator.
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KyuBancho {
    /** Hardcoded port for TCP server. */
    private static final int BANCHO_PORT = 13381;

    public static void main(String[] args) {
        log.info("KyuBancho is starting.");

        ServiceLocator serviceLocator = ServiceLocator.getInstance();

        PingService pingService = new PingService();
        pingService.start();

        ServerBootstrap serverBootstrap = BanchoServerBootstrap.create();
        BanchoServer banchoServer = new BanchoServer(serverBootstrap);

        Thread serverThread = new Thread(() -> banchoServer.bind(BANCHO_PORT));
        serverThread.setDaemon(true);
        serverThread.start();

        ConsoleInputHandler inputHandler = new ConsoleInputHandler(serviceLocator.getUserDAO(), new Scanner(System.in));
        inputHandler.start();
    }
}
