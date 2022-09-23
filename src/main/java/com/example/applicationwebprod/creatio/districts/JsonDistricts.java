package com.example.applicationwebprod.creatio.districts;

import lombok.Data;

import java.util.List;

@Data
public class JsonDistricts {
    private String Json;
    private List<UBMDistricts> value;
}
