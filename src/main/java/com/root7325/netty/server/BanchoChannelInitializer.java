package com.root7325.netty.server;

import com.root7325.netty.codec.PacketEncoder;
import com.root7325.netty.codec.LoginDataDecoder;
import com.root7325.netty.codec.PacketDecoder;
import com.root7325.netty.handler.BanchoChannelHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;

/**
 * @author kate on 02.05.2025
 */
public class BanchoChannelInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline().addLast(new LoginDataDecoder());
        channel.pipeline().addLast(new PacketDecoder());
        channel.pipeline().addLast(new PacketEncoder());
        channel.pipeline().addLast(new BanchoChannelHandler());
    }
}
