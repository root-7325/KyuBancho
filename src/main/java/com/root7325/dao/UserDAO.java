package com.root7325.dao;

import com.root7325.entity.User;

/**
 * @author kate on 04.05.2025
 */
public interface UserDAO {
    User getUser(String username, String passwordHash);
    void addUser(String username, String password);
    void removeUser(String username);
}
