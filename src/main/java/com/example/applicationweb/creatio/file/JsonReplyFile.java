package com.example.applicationweb.creatio.file;

import com.example.applicationweb.creatio.client.UBMAppClient;
import lombok.Data;

import java.util.List;

@Data
public class JsonReplyFile {
    private String name;
    private List<UBMApplicationsFile> value;
}
