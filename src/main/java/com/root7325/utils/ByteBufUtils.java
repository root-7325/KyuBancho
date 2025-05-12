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
        StringBuilder stringBuilder = new StringBuilder();

        while (buf.isReadable()) {
            byte temp = buf.readByte();

            if (temp == 13) {
                buf.readByte();
                break;
            } else {
                stringBuilder.append((char) temp);
            }
        }

        return stringBuilder.toString();
    }

    public static String readString(ByteBuf buf) {
        if (buf.readByte() == 0) {
            return "";
        };
        int len = read7BitInt(buf);
        return buf.readCharSequence(len, StandardCharsets.UTF_8).toString();
    }

    public static void writeString(String str, ByteBuf buf) {
        if (str == null) {
            buf.writeByte(0);
            return;
        }
        buf.writeByte(11);
        byte[] string = str.getBytes(StandardCharsets.UTF_8);
        write7BitInt(string.length, buf);
        buf.writeBytes(string);
    }

    public static int read7BitInt(ByteBuf buf) {
        int num = 0;
        int num2 = 0;
        while (num2 != 35) {
            byte b = buf.readByte();
            num |= (int) (b & 127) << num2;
            num2 += 7;
            if ((b & 128) == 0) {
                return num;
            }
        }
        return 0;
    }

    public static void write7BitInt(int i, ByteBuf buf) {
        int num;
        for (num = i; num >= 128; num >>= 7) {
            buf.writeByte((byte)(num | 128));
        }
        buf.writeByte((byte)num);
    }
}
