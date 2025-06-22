package com.root7325.netty.server;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.root7325.netty.codec.PacketEncoder;
import com.root7325.netty.codec.LoginDataDecoder;
import com.root7325.netty.codec.PacketDecoder;
import com.root7325.netty.handler.BanchoChannelHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import lombok.AllArgsConstructor;

/**
 * @author kate on 02.05.2025
 */
@AllArgsConstructor(onConstructor = @__({@Inject}))
public class BanchoChannelInitializer extends ChannelInitializer<Channel> {
    private final Injector injector;

    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline().addLast(new LoginDataDecoder());
        channel.pipeline().addLast(new PacketDecoder());
        channel.pipeline().addLast(new PacketEncoder());
        channel.pipeline().addLast(injector.getInstance(BanchoChannelHandler.class));
    }
}
