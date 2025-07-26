package com.root7325.netty.handler;

import com.google.inject.Inject;
import com.root7325.bancho.core.BanchoSession;
import com.root7325.bancho.core.PacketRouter;
import com.root7325.bancho.core.ISessionManager;
import com.root7325.bancho.packet.AbstractPacket;
import com.root7325.netty.codec.LoginDataDecoder;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.SocketException;

/**
 * @author kate on 02.05.2025
 */
@Slf4j
@AllArgsConstructor(onConstructor = @__({@Inject}))
public class BanchoChannelHandler extends ChannelInboundHandlerAdapter {
    private final PacketRouter router;
    private final ISessionManager sessionManager;
    private BanchoSession banchoSession;

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        log.info("{} is active!", ctx.channel().remoteAddress());

        this.banchoSession = new BanchoSession();
        this.banchoSession.setChannel(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        log.info("{} is inactive!", ctx.channel().remoteAddress());
        sessionManager.removeSession(banchoSession);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg == null) {
            return;
        }

        if (msg instanceof LoginDataDecoder.LoginData) {
            router.handleLogin((LoginDataDecoder.LoginData) msg, banchoSession);
        }

        if (msg instanceof AbstractPacket) {
            router.handle((AbstractPacket) msg, banchoSession);
        }

        banchoSession.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        if (!(cause instanceof SocketException)) {
            log.error("Exception caught!", cause);
        }
        ctx.close();
    }
}
