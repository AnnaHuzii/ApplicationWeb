package com.example.applicationwebprod.creatio.client;

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
    private String UBMDistrictId;
    private String Address;
    private String UBMHouseNumber;
    private String UBMContactNumberOfPeople;
    private String GivenName;
    private String MiddleName;
}

