package com.example.applicationweb.creatio.user;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.*;

import java.io.IOException;
import java.util.List;

public class UserHttp {
    private static final int RESPONCE_CODE_OK = 200;
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static List<String> logsUser(String url, Object login) throws IOException {
        String requestBody = GSON.toJson(login);
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json; odata=verbose; IEEE754Compatible=true");
        RequestBody body = RequestBody.create(requestBody, mediaType);
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .addHeader("ForceUseSession", "true")
                .build();
        Response response = client.newCall(request).execute();

        List<String> cookies = response.headers("Set-cookie");


            if (response.code() == RESPONCE_CODE_OK) {
                return cookies;
            } else {
                return null;
            }
    }


}



