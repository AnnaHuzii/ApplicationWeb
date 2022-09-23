package com.example.applicationwebprod.creatio.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private String UserName;
    private String UserPassword;
}
