package com.wkclz.generator.server.bean.gen;

import lombok.Data;

import java.io.Serializable;

@Data
public class GenTable implements Serializable {

    // 表名
    private String tableName;
    // 表备注
    private String tableComment;
    // 实体名
    private String entityName;
    // 实体变量名
    private String variableName;

    // Rest 接口分组名
    private String restGroupName;
    // Rest 访问路径前缀
    private String restPathPrefix;

    // 导入 datetime 包
    private String datetimeImport;
    // 导入 bigdecimal 包
    private String bigdecimalImport;

}
