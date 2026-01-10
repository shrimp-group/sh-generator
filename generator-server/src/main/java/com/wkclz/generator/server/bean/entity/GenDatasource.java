package com.wkclz.generator.server.bean.entity;

import com.wkclz.core.annotation.Desc;
import com.wkclz.core.base.BaseEntity;
import lombok.Data;

@Data
public class GenDatasource extends BaseEntity {

    @Desc("用户名")
    private String userCode;

    @Desc("数据库类型")
    private String dbType;

    @Desc("数据源编码")
    private String dbCode;

    @Desc("主机名")
    private String dbHost;

    @Desc("端口")
    private Integer dbPort;

    @Desc("数据库名称")
    private String dbSchema;

    @Desc("数据库用户名")
    private String dbUsername;

    @Desc("数据库密码")
    private String dbPassword;
}
