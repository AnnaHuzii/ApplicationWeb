package com.example.applicationweb.creatio.client;

import lombok.Data;

import java.util.List;

@Data
public class JsonGet {
    private String Json;
    private List<UBMAppClient> value;
}
