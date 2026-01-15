package com.wkclz.generator.server.bean.gen;

import lombok.Data;

import java.io.Serializable;

@Data
public class GenPkg implements Serializable {

    private String pkgPath;
    private String projectBasePath;
    private String tempCode;

}
