package com.wkclz.generator.server.bean.entity;

import com.wkclz.core.annotation.Desc;
import com.wkclz.core.base.BaseEntity;
import lombok.Data;

@Data
public class GenProject extends BaseEntity {

    @Desc("项目编码")
    private String projectCode;

    @Desc("用户名")
    private String userCode;

    @Desc("数据库编码")
    private String dbCode;

    @Desc("表前缀")
    private String tablePrefix;

    @Desc("模块名(英文)")
    private String moduleName;

    @Desc("项目名称(唯一标识)")
    private String projectName;

    @Desc("项目说明(中文名)")
    private String projectDesc;
}
