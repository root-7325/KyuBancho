package com.root7325.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.Builder;

/**
 * @author kate on 02.05.2025
 */
@Builder
public class BanchoServerBootstrap {
    private final int bossThreads = 1;
    private final int workerThreads = 1;
    private final int soBacklog = 100;

    public static ServerBootstrap create(BanchoServerBootstrap banchoServerBootstrap) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        return new ServerBootstrap()
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, banchoServerBootstrap.soBacklog)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new BanchoChannelInitializer());
    }

    public static ServerBootstrap create() {
        return create(BanchoServerBootstrap.builder().build());
    }
}
