package com.example.applicationweb.creatio.file;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UBMApplicationsFile {
    private String Id;
    private String Name;
    private String TypeId;
    private String Size;
    private String SysFileStorageId;
    private String UBMApplicationsId;
    private String FileGroupId;
}