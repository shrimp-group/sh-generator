package com.wkclz.generator.server.bean.entity;

import com.wkclz.core.annotation.Desc;
import com.wkclz.core.base.BaseEntity;
import lombok.Data;

import java.util.Date;

@Data
public class GenLog extends BaseEntity {

    @Desc("用户名")
    private String userCode;

    @Desc("项目编码")
    private String projectCode;

    @Desc("授权码")
    private String authCode;

    @Desc("生成路径")
    private String genPath;

    @Desc("开始时间")
    private Date startTime;

    @Desc("结束时间")
    private Date endTime;
}
