package com.wkclz.generator.server.bean.response;

import com.wkclz.web.bean.EntityResp;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "数据源分页请求")
public class DatasourcePageResp extends EntityResp {

    @Schema(description = "用户名")
    private String userCode;
    @Schema(description = "数据源编码")
    private String dbCode;
    @Schema(description = "数据库类型")
    private String dbType;
    @Schema(description = "主机名")
    private String dbHost;
    @Schema(description = "端口")
    private Integer dbPort;
    @Schema(description = "数据库名称")
    private String dbSchema;
    @Schema(description = "数据库用户名")
    private String dbUsername;
    @Schema(description = "数据库密码, 已脱敏")
    private String dbPassword;

}
