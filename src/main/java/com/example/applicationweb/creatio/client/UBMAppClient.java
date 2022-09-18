package com.example.applicationweb.creatio.client;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UBMAppClient {
    private String Id;
    private String Name;
    private String TypeId;
    private String BirthDate;
    private String MobilePhone;
    private String GivenName;
    private String MiddleName;
}

