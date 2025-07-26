package com.root7325.config;

import lombok.Getter;
import lombok.Setter;

/**
 * Configuration class for database.
 *
 * @author root7325 on 20.07.2025
 */
@Getter
@Setter
public class DatabaseConfig {
    private String url;
    private String username;
    private String password;
    private boolean showSql;
    private String hbm2ddlAuto;

    /**
     * Constructs full JDBC URL for database connection.
     *
     * @return JDBC URL as a string.
     */
    public String getUrl() {
        return "jdbc:mysql://" + url;
    }
}
