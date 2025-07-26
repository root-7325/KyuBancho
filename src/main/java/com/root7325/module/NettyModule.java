package com.root7325.module;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.root7325.bancho.core.ISessionManager;
import com.root7325.bancho.core.PacketRouter;
import com.root7325.bancho.core.SessionManager;
import com.root7325.netty.server.BanchoServer;
import com.root7325.netty.server.BanchoServerBootstrap;

/**
 * This module configures Netty server, channel handlers, and networking dependencies.
 *
 * @author root7325 on 22.06.2025
 */
public class NettyModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ISessionManager.class).to(SessionManager.class).in(Singleton.class);
        bind(PacketRouter.class).in(Singleton.class);
        bind(BanchoServer.class).in(Singleton.class);
        bind(BanchoServerBootstrap.class).in(Singleton.class);
    }
}
