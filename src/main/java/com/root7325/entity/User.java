package com.root7325.entity;

import com.root7325.bancho.enums.Permissions;
import jakarta.persistence.*;
import lombok.Data;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author kate on 02.05.2025
 */
@Data
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
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

    @Column(name="score_rank")
    public int rank;

    @Column(name="avatar_filename")
    public String avatarFilename = "";

    public Permissions permissions = Permissions.Normal;

    @PrePersist
    void prePersist() {
        try {
            this.setPasswordHash(hashPassword(getPasswordHash()));
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        byte[] bytes = password.getBytes(StandardCharsets.UTF_8);
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] digest = md.digest(bytes);
        
        StringBuilder hexString = new StringBuilder();
        for (byte b : digest) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    @PostPersist
    void postPersist() {
        this.setRank(getId());
    }
}
