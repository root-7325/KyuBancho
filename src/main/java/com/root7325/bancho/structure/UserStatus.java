package com.root7325.bancho.structure;

import com.root7325.bancho.enums.Mods;
import com.root7325.bancho.enums.PlayModes;
import com.root7325.bancho.enums.Status;
import com.root7325.utils.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import lombok.Data;

/**
 * @author kate on 03.05.2025
 */
@Data
public class UserStatus {
    private Status status;
    private boolean beatmapUpdate;
    private String statusText;
    private String songChecksum;
    public int beatmapId;
    public Mods mods;
    public PlayModes playMode;

    public UserStatus() {
        this.status = Status.Unknown;
        this.beatmapUpdate = false;
    }

    public UserStatus(ByteBuf in) {
        this.status = Status.values()[in.readByte()];
        this.beatmapUpdate = in.readBoolean();

        if (beatmapUpdate) {
            this.statusText = ByteBufUtils.readString(in);
            this.songChecksum = ByteBufUtils.readString(in);

            in.readShortLE(); // mods
            this.playMode = PlayModes.values()[in.readByte()];
            this.beatmapId = in.readIntLE();
        }
    }

    public void writeToStream(ByteBuf out) {
        out.writeByte(status.ordinal());
        out.writeBoolean(beatmapUpdate);

        if (beatmapUpdate) {
            ByteBufUtils.writeString(statusText, out);
            ByteBufUtils.writeString(songChecksum, out);
            out.writeShortLE(Mods.None.value());
            out.writeByte(playMode.ordinal());
            out.writeIntLE(beatmapId);
        }
    }
}
