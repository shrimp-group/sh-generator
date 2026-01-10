package com.wkclz.generator.server.bean.entity;

import com.wkclz.core.annotation.Desc;
import com.wkclz.core.base.BaseEntity;
import lombok.Data;

@Data
public class GenTask extends BaseEntity {

    @Desc("用户名")
    private String userCode;

    @Desc("项目编码")
    private String projectCode;

    @Desc("模板编码")
    private String tempCode;

    @Desc("任务名称")
    private String taskName;

    @Desc("是否生成")
    private Integer needCreate;

    @Desc("是否删除(本地模式有效)")
    private Integer needDelete;

    @Desc("任务项目基本路径")
    private String projectBasePath;

    @Desc("任务包路径")
    private String packagePath;

    @Desc("任务描述")
    private String taskDesc;
}
