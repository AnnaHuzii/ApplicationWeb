package com.example.applicationweb.creatio.file;

import com.example.applicationweb.connection.StorageUser;
import com.example.applicationweb.controller.CreateDaoService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileHttp {
    private static final CreateDaoService creatioOrderDaoService = new CreateDaoService();
    private static final StorageUser util = StorageUser.getInstance();
    private static final OkHttpClient CLIENT = new OkHttpClient().newBuilder().build();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final int RESPONCE_CODE_OK = 201;

    public static boolean addFile(String url, Object file) throws IOException {
        String authorization = creatioOrderDaoService.authorization(util.getConnection());

        List<String> list = new ArrayList<>();
        String[] cookies = authorization.split(";");
        Collections.addAll(list, cookies);
        String cookie = list.get(2);
        List<String> listCookie = new ArrayList<>();
        String[] cookiesName = cookie.split("=");
        Collections.addAll(listCookie, cookiesName);
        String cookieValue = listCookie.get(1);

        String requestBody = GSON.toJson(file);
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

//        if (response.code() == RESPONCE_CODE_OK) {
//            String myResponse = response.body().string();
//            UBMApplicationsFile jsonGet = GSON.fromJson(myResponse, UBMApplicationsFile.class);
//            String idFile = jsonGet.getId();
//            UBMApplicationsFileData jsonData = GSON.fromJson(myResponse, UBMApplicationsFileData.class);
//            boolean result = putFile(url, idFile, jsonData);
//            return result;
//        }
        return (response.code() == RESPONCE_CODE_OK);
    }

    public static boolean putFile(String url, String idFile, UBMApplicationsFileData data) throws IOException {
        String authorization = creatioOrderDaoService.authorization(util.getConnection());
        String fileURL = String.format("%s(%s)/%s", url, idFile, "Data");

        List<String> list = new ArrayList<>();
        String[] cookies = authorization.split(";");
        Collections.addAll(list, cookies);
        String cookie = list.get(2);
        List<String> listCookie = new ArrayList<>();
        String[] cookiesName = cookie.split("=");
        Collections.addAll(listCookie, cookiesName);
        String cookieValue = listCookie.get(1);

        String requestBody = GSON.toJson(data);
        MediaType mediaType = MediaType.parse("application/octet-stream; IEEE754Compatible=true");
        RequestBody body = RequestBody.create(requestBody, mediaType);
        Request request = new Request.Builder()
                .url(fileURL)
                .method("PUT", body)
                .addHeader("Accept", "application/json; text/plain; */*")
                .addHeader("Content-Type", "application/octet-stream; IEEE754Compatible=true")
                .addHeader("Cookie", authorization)
                .addHeader("BPMCSRF", cookieValue)
                .build();
        Response response = CLIENT.newCall(request).execute();

        return (response.code() == RESPONCE_CODE_OK);
    }
}



