package com.example.applicationwebprod.connection;

import lombok.Getter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Getter
public class PropertiesReader {
    private String URL_ORDEG;
    private String URL_CLIENT;
    private String URL_DISTRICTS;
    private String URL_FILE;

    public PropertiesReader() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream reader = loader.getResourceAsStream("url.properties");

        Properties properties = new Properties();
        try {
            properties.load(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        URL_CLIENT =  properties.getProperty("client.connection.url");
        URL_ORDEG = properties.getProperty("order.connection.url");
        URL_DISTRICTS = properties.getProperty("districts.connection.url");
        URL_FILE = properties.getProperty("file.connection.url");
    }

}
