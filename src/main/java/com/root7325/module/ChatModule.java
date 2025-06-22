package com.root7325.module;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.root7325.bancho.chat.ChannelManager;

/**
 * @author root7325 on 22.06.2025
 */
public class ChatModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ChannelManager.class).in(Singleton.class);
    }
}
