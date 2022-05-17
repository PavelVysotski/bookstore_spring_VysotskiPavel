package com.belhard.dao.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ResourcesReader {

    private static final Logger logger = LogManager.getLogger(ResourcesReader.class);

    private Properties properties;
    private final String connectionCredFilePath = "C:\\IntelliJProject\\belhard\\src\\main\\resources\\connection_cred.properties";

    public ResourcesReader() {
        logger.debug("Reading connection_cred.properties file.");
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(connectionCredFilePath));
            properties = new Properties();
            try {
                properties.load(reader);
                reader.close();
            } catch (IOException e) {
                logger.error("File reading issues.", e);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("connection_cred.properties not found.");
        }
    }

    public String getDbUrl() {
        return readProperty("dbUrl.remote", "dbUrl.local", "dbUrl not specified in the connection_cred.properties file.");
    }


    public String getLogin() {
        return readProperty("login.remote", "login.local", "login not specified in the connection_cred.properties file.");
    }


    public String getPassword() {
        return readProperty("password.remote", "password.local", "password not specified in the connection_cred.properties file.");
    }

    private String readProperty(String remote, String local, String errMessage) {
        String property = isDbRemote() ? properties.getProperty(remote) : properties.getProperty(local);
        if (property != null) {
            return property;
        } else {
            throw new RuntimeException(errMessage);
        }
    }

    private boolean isDbRemote() {
        if (properties.getProperty("db.remote") != null) {
            return properties.getProperty("db.remote").equals("true");
        } else {
            throw new RuntimeException("Parameter not set in VM options.");
        }
    }
}
