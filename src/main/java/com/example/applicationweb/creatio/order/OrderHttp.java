package com.example.applicationweb.creatio.order;

import com.example.applicationweb.connection.StorageUser;
import com.example.applicationweb.controller.CreateDaoService;
import com.example.applicationweb.creatio.client.UBMAppClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrderHttp {
    private static final CreateDaoService creatioOrderDaoService = new CreateDaoService();
    private static final StorageUser util = StorageUser.getInstance();
    private static final OkHttpClient CLIENT = new OkHttpClient().newBuilder().build();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final int RESPONCE_CODE_OK = 201;

    public static String sendingOrder(String url, Object order) throws IOException {
        String authorization = creatioOrderDaoService.
                authorization(util.getConnection());

        List<String> list = new ArrayList<>();
        String [] cookies = authorization.split(";");
        Collections.addAll(list, cookies);
        String cookie = list.get(2);
        List<String> listCookie = new ArrayList<>();
        String [] cookiesName = cookie.split("=");
        Collections.addAll(listCookie, cookiesName);
        String cookieValue = listCookie.get(1);

        String requestBody = GSON.toJson(order);
        MediaType mediaType = MediaType.parse("application/json; odata=verbose; IEEE754Compatible=true");
        RequestBody body = RequestBody.create(requestBody, mediaType);
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .addHeader("Accept", "application/json; odata=verbose")
                .addHeader("Content-Type", "application/json; odata=verbose; IEEE754Compatible=true")
                .addHeader("Cookie", authorization)
                .addHeader("BPMCSRF", cookieValue)
                .build();
        Response response = CLIENT.newCall(request).execute();
        if (response.code() == RESPONCE_CODE_OK){
            String myResponse = response.body().string();
            UBMAppClient client = GSON.fromJson(myResponse, UBMAppClient.class);
            return client.getId();
        }
        return "false";
    }
}



