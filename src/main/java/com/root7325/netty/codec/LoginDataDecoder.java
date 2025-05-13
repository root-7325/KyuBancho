package com.root7325.netty.codec;

import com.root7325.utils.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author kate on 02.05.2025
 */
@Slf4j
public class LoginDataDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {

        LoginData loginData = new LoginData();
        loginData.setUsername(ByteBufUtils.readNETString(byteBuf));
        loginData.setPasswordHash(ByteBufUtils.readNETString(byteBuf));
        byteBuf.clear();

        list.add(loginData);
        channelHandlerContext.pipeline().remove(LoginDataDecoder.class);
    }

    @Data
    public static class LoginData {
        public String username;
        public String passwordHash;
    }
}
