package com.wkclz.generator.server.bean.entity;

import com.wkclz.core.annotation.Desc;
import com.wkclz.core.base.BaseEntity;
import lombok.Data;

@Data
public class GenTemplate extends BaseEntity {

    @Desc("模板编码")
    private String tempCode;

    @Desc("用户名")
    private String userCode;

    @Desc("模板名称")
    private String tempName;

    @Desc("生成的文件后缀")
    private String tempSubfix;

    @Desc("模板描述")
    private String tempDesc;

    @Desc("模板内容")
    private String tempContent;
}
