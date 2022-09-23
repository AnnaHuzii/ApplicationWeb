package com.example.applicationweb.creatio.districts;

import lombok.Data;

import java.util.List;

@Data
public class JsonDistricts {
    private String Json;
    private List<UBMDistricts> value;
}
