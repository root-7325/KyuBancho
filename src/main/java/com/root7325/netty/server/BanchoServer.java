package com.root7325.netty.server;

import com.google.inject.Inject;
import com.root7325.config.Config;
import com.root7325.config.ServerConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author kate on 02.05.2025
 * <p>
 * Main server class that handles TCP connections.
 * Manages the Netty server bootstrap and binding.
 */
@Slf4j
public class BanchoServer {
    private final String host;
    private final int port;
    private final ServerBootstrap serverBootstrap;

    @Inject
    public BanchoServer(Config config, BanchoServerBootstrap banchoServerBootstrap) {
        ServerConfig serverConfig = config.getServerConfig();
        this.host = serverConfig.getHost();
        this.port = serverConfig.getPort();

        this.serverBootstrap = banchoServerBootstrap.create();
    }

    public void bind() {
        log.debug("Binding to :{}...", port);
        try {
            ChannelFuture channelFuture = serverBootstrap.bind(host, port);
            channelFuture.addListener(future -> {
                if (future.isSuccess()) {
                    log.info("Server successfully bound to {}:{}!", host, port);
                } else {
                    log.error("Failed to bind to {}:{}.", host, port, future.cause());
                }
            });
        } catch (Exception ex) {
            log.error("We're got dirty cookie...", ex);
        }
    }
}
