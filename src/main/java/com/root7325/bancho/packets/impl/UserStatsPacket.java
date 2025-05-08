package com.root7325.bancho.packets.impl;

import com.root7325.bancho.enums.Completeness;
import com.root7325.bancho.packets.AbstractPacket;
import com.root7325.bancho.packets.PacketType;
import com.root7325.bancho.structures.UserStatus;
import com.root7325.entities.User;
import com.root7325.utils.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import lombok.NoArgsConstructor;

/**
 * @author kate on 03.05.2025
 */
@NoArgsConstructor
public class UserStatsPacket extends AbstractPacket {
    private User user;
    private UserStatus userStatus;
    private Completeness completeness;

    public UserStatsPacket(User user, UserStatus userStatus, Completeness completeness) {
        this.setPacketType(PacketType.Bancho_HandleOsuUpdate);
        this.user = user;
        this.userStatus = userStatus;
        this.completeness = completeness;
    }

    public UserStatsPacket(User user, UserStatus userStatus) {
        this(user, userStatus, Completeness.Full);
    }

    @Override
    public void readFromStream(ByteBuf in) {

    }

    @Override
    public void writeToStream(ByteBuf out) {
        out.writeIntLE(user.getId());
        out.writeByte(completeness.ordinal());
        userStatus.writeToStream(out);
        if (completeness.ordinal() > Completeness.StatusOnly.ordinal()) {
            out.writeLongLE(user.getRankedScore());
            out.writeFloatLE(user.getAccuracy());
            out.writeIntLE(user.getPlayCount());
            out.writeLongLE(user.getTotalScore());
            out.writeIntLE(user.getId()); // rank!
        }
        if (completeness == Completeness.Full) {
            ByteBufUtils.writeString(user.getUsername(), out);
            ByteBufUtils.writeString(user.getAvatarFilename(), out);
            out.writeByte(24);
            ByteBufUtils.writeString("Russia", out); // todo: country
            out.writeByte(user.getPermissions().getValue());
        }
    }
}
