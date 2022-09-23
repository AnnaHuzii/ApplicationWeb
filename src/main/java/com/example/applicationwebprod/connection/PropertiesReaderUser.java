package com.example.applicationwebprod.connection;

import lombok.Getter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Getter
public class PropertiesReaderUser {
    private String DB_URL;
    private String DB_USERNAME;
    private String DB_PASSWORD;

    public PropertiesReaderUser() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream reader = loader.getResourceAsStream("login.properties");

        Properties properties = new Properties();
        try {
            properties.load(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        DB_URL = properties.getProperty("login.connection.url");
        DB_USERNAME = properties.getProperty("login.connection.username");
        DB_PASSWORD = properties.getProperty("login.connection.password");
    }

}
