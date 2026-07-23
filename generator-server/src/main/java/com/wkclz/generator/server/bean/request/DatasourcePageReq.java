package com.wkclz.generator.server.bean.request;

import com.wkclz.web.bean.PageReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "数据源分页请求")
public class DatasourcePageReq extends PageReq {

    @Schema(description = "用户名")
    private String userCode;

    @Schema(description = "数据源编码， 模糊搜索")
    private String dbCode;

    @Schema(description = "数据库类型")
    private String dbType;

    @Schema(description = "主机名， 模糊搜索")
    private String dbHost;

    @Schema(description = "数据库名称， 模糊搜索")
    private String dbSchema;




}
