package com.root7325.utils;

import io.netty.buffer.ByteBuf;
import io.netty.util.internal.StringUtil;

import java.nio.charset.StandardCharsets;

/**
 * @author root7325 on 18.04.2024
 */
public class ByteBufUtils {
    /**
     *
     * @param buf ByteBuf containing .NET type string
     * @return read from ByteBuf string
     */
    public static String readNETString(ByteBuf buf) {
        if (buf == null) {
            throw new IllegalArgumentException("ByteBuf can't be null!");
        }
        StringBuilder stringBuilder = new StringBuilder();

        while (buf.isReadable()) {
            byte temp = buf.readByte();
            if (temp == 13) {
                buf.readByte();
                break;
            }
            stringBuilder.append((char) temp);
        }
        
        return stringBuilder.toString();
    }

    public static String readString(ByteBuf buf) {
        if (buf == null) {
            throw new IllegalArgumentException("ByteBuf can't be null!");
        }

        if (buf.readByte() == 0) {
            return "";
        }
        int len = read7BitInt(buf);
        if (len < 0 || len > buf.readableBytes()) {
            throw new IndexOutOfBoundsException("String length is incorrect!");
        }
        return buf.readCharSequence(len, StandardCharsets.UTF_8).toString();
    }

    public static void writeString(String str, ByteBuf buf) {
        if (buf == null) {
            throw new IllegalArgumentException("ByteBuf can't be null!");
        }

        if (str == null) {
            buf.writeByte(0);
            return;
        }

        byte[] string = str.getBytes(StandardCharsets.UTF_8);
        
        buf.writeByte(11);
        write7BitInt(string.length, buf);
        buf.writeBytes(string);
    }

    public static int read7BitInt(ByteBuf buf) {
        if (buf == null) {
            throw new IllegalArgumentException("ByteBuf can't be null!");
        }

        int num = 0;
        int num2 = 0;
        while (num2 != 35) {
            if (!buf.isReadable()) {
                throw new IndexOutOfBoundsException("Not enough data in buffer!");
            }
            byte b = buf.readByte();
            num |= (b & 127) << num2;
            num2 += 7;
            if ((b & 128) == 0) {
                return num;
            }
        }
        return 0;
    }

    public static void write7BitInt(int i, ByteBuf buf) {
        if (buf == null) {
            throw new IllegalArgumentException("ByteBuf can't be null!");
        }

        int num;
        for (num = i; num >= 128; num >>= 7) {
            buf.writeByte((byte)(num | 128));
        }
        buf.writeByte((byte)num);
    }
}
