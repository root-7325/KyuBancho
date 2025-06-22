package com.root7325.module;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.root7325.dao.UserDAO;
import com.root7325.dao.UserDAOImpl;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * @author root7325 on 22.06.2025
 */
public class DatabaseModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(UserDAO.class).to(UserDAOImpl.class).asEagerSingleton();
    }

    @Provides
    @Singleton
    public SessionFactory provideSessionFactory() {
        try {
            Configuration configuration = new Configuration()
                    .configure("hibernate.cfg.xml");
            return configuration.buildSessionFactory();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create session factory", e);
        }
    }
}
