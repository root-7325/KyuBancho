package com.root7325.entities;

import com.root7325.bancho.enums.Permissions;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

/**
 * @author kate on 02.05.2025
 */
@Data
public class User {
    @Id
    private int id;

    @Column(unique = true)
    private String username;

    @Column(name="password_hash")
    private String passwordHash;

    @Column(name="ranked_score")
    public long rankedScore;

    public float accuracy;

    @Column(name="play_count")
    public int playCount;

    @Column(name="total_score")
    public long totalScore;

    public int rank;

    @Column(name="avatar_filename")
    public String avatarFilename;

    public Permissions permissions = Permissions.Subscriber;
}
