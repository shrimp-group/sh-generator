package com.wkclz.generator.server.bean.gen;

import lombok.Data;

import java.io.Serializable;

@Data
public class GenColumn implements Serializable {

    // 表名
    private String tableName;
    // 字段名
    private String columnName;
    // 字段注释
    private String columnComment;

    // 字段变量名
    private String fieldName;
    // 字段 getter 名
    private String fieldGetterName;
    // 字段 setter 名
    private String fieldSetterName;

    // 字段长度
    private Long maxLength;
    // 数据库类型
    private String dbType;
    // Java 类型
    private String javaType;
    // ts 类型
    private String tsType;
    // 输入类型
    private String inputType;


}
