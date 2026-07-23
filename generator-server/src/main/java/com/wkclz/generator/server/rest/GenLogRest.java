package com.wkclz.generator.server.rest;

import com.wkclz.core.base.PageData;
import com.wkclz.core.base.R;
import com.wkclz.core.enums.ResultCode;
import com.wkclz.generator.server.Route;
import com.wkclz.generator.server.bean.entity.GenLog;
import com.wkclz.generator.server.service.GenLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Route.PREFIX)
@Tag(name = "日志管理", description = "代码生成日志的查询操作")
public class GenLogRest {

    @Autowired
    private GenLogService genLogService;


    @GetMapping(Route.LOG_PAGE)
    @Operation(summary = "日志分页查询", description = "根据条件分页查询日志列表")
    public R<PageData<GenLog>> logPage(
        @Parameter(description = "日志信息") GenLog entity) {
        PageData<GenLog> page = genLogService.getGenLogPage(entity);
        return R.ok(page);
    }

    @GetMapping(Route.LOG_DETAIL)
    @Operation(summary = "日志详情查询", description = "根据ID查询日志详情")
    public R<GenLog> logDetail(
        @Parameter(description = "日志ID", required = true) GenLog entity) {
        Assert.notNull(entity.getId(), ResultCode.PARAM_NO_ID.getMessage());
        GenLog detail = genLogService.selectById(entity.getId());
        return R.ok(detail);
    }

}