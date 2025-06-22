package com.root7325.module;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.root7325.bancho.service.PacketDispatcherServiceImpl;
import com.root7325.bancho.service.interfaces.IPacketDispatcherService;

/**
 * @author root7325 on 22.06.2025
 */
public class ServiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(IPacketDispatcherService.class).to(PacketDispatcherServiceImpl.class).in(Singleton.class);
    }
}
