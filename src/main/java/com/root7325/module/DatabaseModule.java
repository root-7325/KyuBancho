package com.root7325.module;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.root7325.config.DatabaseConfig;
import com.root7325.dao.UserDAO;
import com.root7325.dao.UserDAOImpl;
import com.root7325.entity.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * This module binds DAO to their implementations & provides
 * SessionFactory.
 *
 * @author root7325 on 22.06.2025
 */
public class DatabaseModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(UserDAO.class).to(UserDAOImpl.class).asEagerSingleton();
    }

    @Provides
    @Singleton
    public SessionFactory provideSessionFactory(DatabaseConfig databaseConfig) {
        try {
            Configuration configuration = new Configuration()
                    .setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver")
                    .setProperty("hibernate.connection.url", databaseConfig.getUrl())
                    .setProperty("hibernate.connection.username", databaseConfig.getUsername())
                    .setProperty("hibernate.connection.password", databaseConfig.getPassword())
                    .setProperty("hibernate.show_sql", String.valueOf(databaseConfig.isShowSql()))
                    .setProperty("hibernate.hbm2ddl.auto", databaseConfig.getHbm2ddlAuto())
                    .setProperty("hibernate.current_session_context_class", "thread")
                    .setProperty("hibernate.cache.provider_class", "org.hibernate.cache.NoCacheProvider")
                    .addAnnotatedClass(User.class);

            return configuration.buildSessionFactory();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create session factory", e);
        }
    }
}
