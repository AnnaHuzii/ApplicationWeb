package com.example.applicationweb.creatio.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private String UserName;
    private String UserPassword;
}
