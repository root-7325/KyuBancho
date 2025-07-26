package com.root7325.dao;

import com.google.inject.Inject;
import com.root7325.entity.User;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

/**
 * @author kate on 12.05.2025
 */
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class UserDAOImpl implements UserDAO {
    private final SessionFactory sessionFactory;
    private final ExecutorService executorService;

    @Override
    public CompletableFuture<Optional<User>> getUser(String username, String passwordHash) {
        return CompletableFuture.supplyAsync(() -> {
            try (Session session = sessionFactory.openSession()) {
                return session.createSelectionQuery("from User where username=:username and passwordHash = :hash", User.class)
                        .setParameter("username", username)
                        .setParameter("hash", passwordHash)
                        .uniqueResultOptional();
            }
        }, executorService);
    }

    @Override
    public void addUser(User user) {
        CompletableFuture.supplyAsync(() -> {
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

                try {
                    session.persist(user);
                    session.getTransaction().commit();
                    log.info("Added new user: {}.", user.getUsername());
                    return user;
                } catch (Exception ex) {
                    session.getTransaction().rollback();
                    throw ex;
                }
            }
        }, executorService);
    }

    @Override
    public void removeUser(String username) {
        throw new UnsupportedOperationException("removeUser is not implemented!");
    }
}
