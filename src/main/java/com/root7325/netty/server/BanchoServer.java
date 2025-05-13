package com.root7325.netty.server;

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
@RequiredArgsConstructor
public class BanchoServer {
    private final ServerBootstrap serverBootstrap;

    public void bind(int port) {
        log.debug("Binding to :{}...", port);
        try {
            ChannelFuture channelFuture = serverBootstrap.bind(port);
            channelFuture.addListener(future -> {
                if (future.isSuccess()) {
                    log.info("Server successfully bound to port {}!", port);
                } else {
                    log.error("Failed to bind to port {}.", port, future.cause());
                }
            });
        } catch (Exception ex) {
            log.error("We're got dirty cookie...", ex);
        }
    }
}
