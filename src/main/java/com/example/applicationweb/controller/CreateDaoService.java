package com.example.applicationweb.controller;

import com.example.applicationweb.connection.PropertiesReader;
import com.example.applicationweb.creatio.client.ClientHttp;
import com.example.applicationweb.creatio.client.UBMAppClient;
import com.example.applicationweb.creatio.districts.DistrictsHttp;
import com.example.applicationweb.creatio.file.FileHttp;
import com.example.applicationweb.creatio.file.UBMApplicationsFile;
import com.example.applicationweb.creatio.order.OrderHttp;
import com.example.applicationweb.creatio.order.UsrApplication;

import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class CreateDaoService {
    public Map<String, String> districts() {
        try {
            PropertiesReader propertiesReader = new PropertiesReader();
            String url = propertiesReader.getURL_DISTRICTS();
            return DistrictsHttp.getDistricts(url);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String MobilePhoneClient(String mobilePhone) {
        String replyClient = "";
        try {
            PropertiesReader propertiesReader = new PropertiesReader();
            String url = propertiesReader.getURL_CLIENT();
            replyClient = ClientHttp.allClient(url, mobilePhone);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return replyClient;
    }

    public String creatiClient(UBMAppClient client) {
        String replyClient = "";
        try {
            PropertiesReader propertiesReader = new PropertiesReader();
            String url = propertiesReader.getURL_CLIENT();
            UBMAppClient clientConnection = appClient(client);
            replyClient = ClientHttp.addClient(url, clientConnection);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return replyClient;
    }
    public String createApplicationNew(UsrApplication order, UBMAppClient client) {

        String id = MobilePhoneClient(client.getMobilePhone());
        if (id == null) {
            id = creatiClient(client);
        }

        String connection = "";
        try {

            PropertiesReader propertiesReaderOrder = new PropertiesReader();
            String url = propertiesReaderOrder.getURL_ORDEG();

            UsrApplication orderConnection = createOrder(order, id);
            connection = OrderHttp.sendingOrder(url, orderConnection);


        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!connection.equals("")) {
            return "Замовлення успішно відправлено";
        } else {
            return "Ваше замовлення не може бути відправлено, виникла проблема, спробуйте ще раз";
        }
    }

    public String createApplicationFile(UsrApplication order, UBMAppClient client, UBMApplicationsFile photo) {

        String id = MobilePhoneClient(client.getMobilePhone());
        if (id == null) {
            id = creatiClient(client);
        }

        String idOrder;
        boolean connection = true;
        try {

            PropertiesReader propertiesReaderOrder = new PropertiesReader();
            String url = propertiesReaderOrder.getURL_ORDEG();

            UsrApplication orderConnection = createOrder(order, id);
            idOrder = OrderHttp.sendingOrder(url, orderConnection);

            String urlFile = propertiesReaderOrder.getURL_FILE();
            UBMApplicationsFile fileConnection = file(photo, idOrder);
            connection = FileHttp.addFile(urlFile, fileConnection);

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (connection) {
            return "Замовлення успішно відправлено";
        } else {
            return "Ваше замовлення не може бути відправлено, виникла проблема, спробуйте ще раз";
        }
    }

    public String authorization(List<String> cookies) {

        StringJoiner resultCookie = new StringJoiner(";");
        for (String cookie : cookies) {
            resultCookie.add(cookie.split(";", 2)[0]);
        }
        return resultCookie.toString();
    }

    public static UBMAppClient appClient(UBMAppClient client) {
        return UBMAppClient.builder().
                Name(client.getName()).
                TypeId(client.getTypeId()).
                BirthDate(client.getBirthDate()).
                MobilePhone(client.getMobilePhone()).
                MiddleName(client.getMiddleName()).
                GivenName(client.getGivenName())
                .build();
    }

    public static UsrApplication createOrder(UsrApplication order, String id) {

        return UsrApplication.builder().
                UBMAppClientId(id).
                UBMAppDistrictId(order.getUBMAppDistrictId()).
                UBMAppSreet(order.getUBMAppSreet()).
                UBMAppHousenumber(order.getUBMAppHousenumber()).
                UBMAppNumberOfPeople(order.getUBMAppNumberOfPeople()).
                UBMAppSourceId(order.getUBMAppSourceId()).
                UBMAppStageId(order.getUBMAppStageId()).
                build();
    }

    public static UBMApplicationsFile file(UBMApplicationsFile photo, String idOrder) {

        return UBMApplicationsFile.builder().
                Name(photo.getName()).
                TypeId(photo.getTypeId()).
                Size(photo.getSize()).
                SysFileStorageId(photo.getSysFileStorageId()).
                UBMApplicationsId(idOrder).
                FileGroupId(photo.getFileGroupId()).
                build();

    }

}
