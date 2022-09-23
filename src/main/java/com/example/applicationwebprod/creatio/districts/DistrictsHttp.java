package com.example.applicationwebprod.creatio.districts;

import com.example.applicationwebprod.connection.StorageUser;
import com.example.applicationwebprod.controller.CreateDaoService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DistrictsHttp {
    private static final CreateDaoService creatioOrderDaoService = new CreateDaoService();
    private static final StorageUser util = StorageUser.getInstance();
    private static final OkHttpClient CLIENT = new OkHttpClient().newBuilder().build();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static final int RESPONCE_CODE_OK = 200;

    public static Map<String,String> getDistricts(String url) throws IOException {
        String authorization = creatioOrderDaoService.authorization(util.getConnection());

        Request request = new Request.Builder()
                .url(url)
                .method("GET", null)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .addHeader("ForceUseSession", "true")
                .addHeader("Cookie", authorization)
                .build();

        Response response = CLIENT.newCall(request).execute();
        Map<String, String> listDistricts = new HashMap<>();
        if (response.code() == RESPONCE_CODE_OK) {
            String myResponse = response.body().string();
            JsonDistricts json = GSON.fromJson(myResponse, JsonDistricts.class);
            for (UBMDistricts client :json.getValue()) {
                listDistricts.put(client.getId(),client.getName());
            }
            return listDistricts;
        }
        return null;
    }
}
