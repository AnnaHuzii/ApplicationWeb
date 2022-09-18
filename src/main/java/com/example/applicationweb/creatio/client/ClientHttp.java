package com.example.applicationweb.creatio.client;

import com.example.applicationweb.connection.StorageUser;
import com.example.applicationweb.controller.CreateDaoService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.SneakyThrows;
import okhttp3.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClientHttp {
    private static final CreateDaoService creatioOrderDaoService = new CreateDaoService();
    private static final StorageUser util = StorageUser.getInstance();
    private static final OkHttpClient CLIENT = new OkHttpClient().newBuilder().build();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @SneakyThrows
    public static String allClient(String urlClient, String mobilePhone) {
        String requestURL = String.format("%s?$filter=%s '%s'", urlClient, "MobilePhone eq", mobilePhone);

        String authorization = creatioOrderDaoService.authorization(util.getConnection());

        Request request = new Request.Builder()
                .url(requestURL)
                .method("GET", null)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .addHeader("ForceUseSession", "true")
                .addHeader("Cookie", authorization)
                .build();

        Response response = CLIENT.newCall(request).execute();

        if (response.code() == 200) {
            String myResponse = response.body().string();
            JsonGet jsonGet = GSON.fromJson(myResponse, JsonGet.class);
            for (UBMAppClient client : jsonGet.getValue()) {
                return client.getId();
            }
        }
        return null;
    }

    public static String addClient(String urlClient, UBMAppClient clientJson) throws Exception {
        String authorization = creatioOrderDaoService.authorization(util.getConnection());
        String requestBody = GSON.toJson(clientJson);
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(requestBody, mediaType);

        List<String> list = new ArrayList<>();
        String[] cookies = authorization.split(";");
        Collections.addAll(list, cookies);
        String cookie = list.get(2);
        List<String> listCookie = new ArrayList<>();
        String[] cookiesName = cookie.split("=");
        Collections.addAll(listCookie, cookiesName);
        String cookieValue = listCookie.get(1);

        Request request = new Request.Builder()
                .url(urlClient)
                .method("POST", body)
                .addHeader("Accept", "application/json; odata=verbose")
                .addHeader("Content-Type", "application/json; odata=verbose; IEEE754Compatible=true")
                .addHeader("Cookie", authorization)
                .addHeader("BPMCSRF", cookieValue)
                .build();
        Response response = CLIENT.newCall(request).execute();

        if (response.code() == 201) {
            String myResponse = response.body().string();
            UBMAppClient client = GSON.fromJson(myResponse, UBMAppClient.class);
                return client.getId();
        }
        return null;
    }
}



