package com.example.applicationwebprod.connection;

import com.example.applicationwebprod.creatio.user.User;
import com.example.applicationwebprod.creatio.user.UserHttp;

import java.util.List;

public class StorageUser {
    private static StorageUser INSTANCE;
    private List<String> connection;

    private StorageUser() {
        try {

            PropertiesReaderUser propertiesReaderUser = new PropertiesReaderUser();
            String url = propertiesReaderUser.getDB_URL();
            String username = propertiesReaderUser.getDB_USERNAME();
            String password = propertiesReaderUser.getDB_PASSWORD();

            User loginConnection = login(username, password);
            connection = UserHttp.logsUser(url, loginConnection);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static StorageUser getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new StorageUser();
        }
        return INSTANCE;
    }

    public List<String> getConnection() {
        return connection;
    }

    public static User login(String userName, String password) {

        return User.builder().
                UserName(userName).
                UserPassword(password).build();
    }
}

