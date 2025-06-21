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

/**
 * @author kate on 12.05.2025
 */
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class UserDAOImpl implements UserDAO {
    private final SessionFactory sessionFactory;

    @Override
    public User getUser(String username, String passwordHash) {
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("from User where username=:username and passwordHash = :hash", User.class);
            query.setParameter("username", username);
            query.setParameter("hash", passwordHash);

            return (User) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void addUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPasswordHash(password);

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            session.persist(user);
            transaction.commit();
            log.info("Added new user: {}.", username);
        } catch (Exception e) {
            log.error("Failed on persisting user", e);
        }
    }

    @Override
    public void removeUser(String username) {
        throw new UnsupportedOperationException("removeUser is not implemented!");
    }
}
