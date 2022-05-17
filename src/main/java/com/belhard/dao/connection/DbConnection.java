package com.belhard.dao.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

    private static final Logger logger = LogManager.getLogger(DbConnection.class);

    private static Connection connection;
    private static ResourcesReader resources = new ResourcesReader();

    private static final String DB_URL = resources.getDbUrl();
    private static final String LOGIN = resources.getLogin();
    private static final String PASSWORD = resources.getPassword();

    static {
        logger.info("Getting connection to DB with credentials");
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(DB_URL, LOGIN, PASSWORD);
        } catch (SQLException e) {
            logger.error("Check for correctness of credentials.", e);
        } catch (ClassNotFoundException e) {
            logger.error("Failed to load driver.", e);
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
