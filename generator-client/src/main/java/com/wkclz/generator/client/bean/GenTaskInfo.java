package com.wkclz.generator.client.bean;

import lombok.Data;

@Data
public class GenTaskInfo {

    private String projectCode;
    private String tempCode;
    private String tempKey;
    private String taskName;
    private Integer createSwitch;
    private Integer deleteSwitch;
    private String fileSubfix;
    private String projectBasePath;
    private String packagePath;

}
