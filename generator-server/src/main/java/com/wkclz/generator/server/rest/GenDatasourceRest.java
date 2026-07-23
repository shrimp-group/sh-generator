package com.wkclz.generator.server.rest;

import com.wkclz.core.base.PageData;
import com.wkclz.core.base.R;
import com.wkclz.core.enums.ResultCode;
import com.wkclz.core.identity.IdentityContext;
import com.wkclz.generator.server.Route;
import com.wkclz.generator.server.bean.entity.GenDatasource;
import com.wkclz.generator.server.bean.request.DatasourcePageReq;
import com.wkclz.generator.server.bean.response.DatasourcePageResp;
import com.wkclz.generator.server.service.GenDatasourceService;
import com.wkclz.web.bean.RemoveReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Route.PREFIX)
@Tag(name = "数据源管理", description = "数据库数据源的增删改查操作")
public class GenDatasourceRest {

    @Autowired
    private GenDatasourceService genDatasourceService;


    @GetMapping(Route.DATASOURCE_PAGE)
    @Operation(summary = "1.数据源分页查询", description = "根据条件分页查询数据源列表")
    public R<PageData<DatasourcePageResp>> datasourcePage(DatasourcePageReq req) {
        GenDatasource entity = new GenDatasource();
        BeanUtils.copyProperties(req, entity);
        PageData<GenDatasource> page = genDatasourceService.getGenDatasourcePage(entity);
        List<DatasourcePageResp> respList = page.getRecords().stream().map(t -> {
            if (StringUtils.isBlank(t.getDbPassword())) {
                t.setDbPassword("******");
            }
            DatasourcePageResp p = new DatasourcePageResp();
            BeanUtils.copyProperties(t, p);
            return p;
        }).toList();
        PageData<DatasourcePageResp> convert = PageData.convert(page, respList);
        return R.ok(convert);
    }

    @GetMapping(Route.DATASOURCE_DETAIL)
    @Operation(summary = "数据源详情查询", description = "根据ID查询数据源详情，密码字段不返回")
    public R<GenDatasource> datasourceDetail(
        @Parameter(description = "数据源ID", required = true) GenDatasource entity) {
        Assert.notNull(entity.getId(), ResultCode.PARAM_NO_ID.getMessage());
        GenDatasource detail = genDatasourceService.selectById(entity.getId());
        detail.setDbPassword(null);
        return R.ok(detail);
    }

    @PostMapping(Route.DATASOURCE_CREATE)
    @Operation(summary = "新增数据源", description = "创建新的数据库数据源")
    public R<Integer> datasourceCreate(
        @Parameter(description = "数据源信息", required = true) @RequestBody GenDatasource entity) {
        paramCheck(entity);
        int insert = genDatasourceService.create(entity);
        return R.ok(insert);
    }

    @PostMapping(Route.DATASOURCE_UPDATE)
    @Operation(summary = "更新数据源", description = "更新数据源信息")
    public R<Integer> datasourceUpdate(
        @Parameter(description = "数据源信息", required = true) @RequestBody GenDatasource entity) {
        paramCheck(entity);
        int i = genDatasourceService.update(entity);
        return R.ok(i);
    }

    @PostMapping(Route.DATASOURCE_REMOVE)
    @Operation(summary = "删除数据源", description = "根据ID删除数据源")
    public R<Integer> datasourceRemove(@RequestBody RemoveReq entity) {
        Assert.notNull(entity.getId(), ResultCode.PARAM_NO_ID.getMessage());
        int i = genDatasourceService.deleteById(entity.getId());
        return R.ok(i);
    }

    @GetMapping(Route.DATASOURCE_OPTIONS)
    @Operation(summary = "数据源选项查询", description = "获取所有数据源选项列表")
    public R<List<GenDatasource>> datasourceOptions() {
        List<GenDatasource> list = genDatasourceService.getDatasourceOptions();
        return R.ok(list);
    }


    private void paramCheck(GenDatasource entity) {
        if (entity.getId() == null) {
            entity.setUserCode(IdentityContext.getUserCode());
            Assert.notNull(entity.getDbPassword(), "dbPassword 不能为空");
        } else {
            Assert.notNull(entity.getDbCode(), "dbCode 不能为空");
            Assert.notNull(entity.getId(), "id 不能为空");
            Assert.notNull(entity.getVersion(), "version 不能为空");
        }
        Assert.notNull(entity.getDbType(), "dbType 不能为空");
        Assert.notNull(entity.getDbHost(), "dbHost 不能为空");
        Assert.notNull(entity.getDbPort(), "dbPort 不能为空");
        Assert.notNull(entity.getDbSchema(), "dbSchema 不能为空");
        Assert.notNull(entity.getDbUsername(), "dbUsername 不能为空");
    }
}
