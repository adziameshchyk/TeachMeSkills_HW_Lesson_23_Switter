package com.tms.connector;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class PostgreSQLConnector {

    private static final String DATABASE_CONNECTIVITY_PROPERTY_FILE_PATH = "src/main/resources/app.properties";
    private static final String PROPERTY_FILE_KEY_DATABASE_URL = "db.url";
    private static final String PROPERTY_FILE_KEY_DATABASE_USER = "db.user";
    private static final String PROPERTY_FILE_KEY_DATABASE_PASSWORD = "db.password";
    private static final String POSTGRESQL_DRIVER_CLASS = "org.postgresql.Driver";

    public static final String DRIVER_IS_NOT_FOUND_MESSAGE = "PostgreSQL JDBC Driver is not found. Include it in your library path.";
    public static final String FAILED_TO_READ_PROPERTY_FILE_MESSAGE = "Failed to read property file.";
    public static final String FAILED_TO_DATABASE_CONNECTION_MESSAGE = "Failed to establish database connection.";

    private static Connection connection;
    private static String DATABASE_URL;
    private static String DATABASE_USER;
    private static String DATABASE_PASSWORD;

    static {
        init();
        loadProperties();
    }
    public static Connection getConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            return connection;
        }

        try {
            return connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
        } catch (SQLException e) {
            System.out.println(FAILED_TO_DATABASE_CONNECTION_MESSAGE);
            e.printStackTrace();
        }
        return null;
    }

    private PostgreSQLConnector() {
    }

    private static void init() {
        try {
            Class.forName(POSTGRESQL_DRIVER_CLASS);
        } catch (ClassNotFoundException e) {
            System.out.println(DRIVER_IS_NOT_FOUND_MESSAGE);
            e.printStackTrace();
        }
        System.out.println("init");
    }

    private static void loadProperties() {
        Properties properties = new Properties();
        try (FileInputStream propertiesFileInputStream = new FileInputStream(DATABASE_CONNECTIVITY_PROPERTY_FILE_PATH)) {
            properties.load(propertiesFileInputStream);
        } catch (IOException e) {
            System.out.println(FAILED_TO_READ_PROPERTY_FILE_MESSAGE);
            e.printStackTrace();
        }

        DATABASE_URL = properties.getProperty(PROPERTY_FILE_KEY_DATABASE_URL);
        DATABASE_USER = properties.getProperty(PROPERTY_FILE_KEY_DATABASE_USER);
        DATABASE_PASSWORD = properties.getProperty(PROPERTY_FILE_KEY_DATABASE_PASSWORD);
        System.out.println("proper");
    }

}
