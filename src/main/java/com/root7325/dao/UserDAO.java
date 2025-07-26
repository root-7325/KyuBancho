package com.root7325.dao;

import com.root7325.entity.User;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * @author kate on 04.05.2025
 */
public interface UserDAO {
    CompletableFuture<Optional<User>> getUser(String username, String passwordHash);
    void addUser(User user);
    void removeUser(String username);
}
