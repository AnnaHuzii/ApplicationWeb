package com.example.applicationweb.creatio.order;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsrApplication {
    private String Id;
    private String UBMAppClientId;
    private String UBMAppDistrictId;
    private String UBMAppNumberOfPeople;
    private String UBMAppSreet;
    private String UBMAppHousenumber;
    private String UBMAppSourceId;
    private String UBMAppStageId;
}
