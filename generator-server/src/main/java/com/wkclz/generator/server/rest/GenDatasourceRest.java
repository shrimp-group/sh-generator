package com.wkclz.generator.server.rest;

import com.wkclz.core.base.PageData;
import com.wkclz.core.base.R;
import com.wkclz.core.enums.ResultCode;
import com.wkclz.generator.server.Route;
import com.wkclz.generator.server.bean.entity.GenDatasource;
import com.wkclz.generator.server.service.GenDatasourceService;
import com.wkclz.iam.sdk.helper.SessionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Route.PREFIX)
public class GenDatasourceRest {

    @Autowired
    private GenDatasourceService genDatasourceService;


    @GetMapping(Route.DATASOURCE_PAGE)
    public R datasourcePage(GenDatasource entity) {
        PageData<GenDatasource> page = genDatasourceService.getGenDatasourcePage(entity);
        return R.ok(page);
    }

    @GetMapping(Route.DATASOURCE_DETAIL)
    public R datasourceDetail(GenDatasource entity) {
        Assert.notNull(entity.getId(), ResultCode.PARAM_NO_ID.getMessage());
        GenDatasource detail = genDatasourceService.selectById(entity.getId());
        return R.ok(detail);
    }

    @PostMapping(Route.DATASOURCE_CREATE)
    public R datasourceCreate(@RequestBody GenDatasource entity) {
        paramCheck(entity);
        int insert = genDatasourceService.create(entity);
        return R.ok(insert);
    }

    @PostMapping(Route.DATASOURCE_UPDATE)
    public R datasourceUpdate(@RequestBody GenDatasource entity) {
        paramCheck(entity);
        int i = genDatasourceService.update(entity);
        return R.ok(i);
    }

    @PostMapping(Route.DATASOURCE_REMOVE)
    public R datasourceRemove(@RequestBody GenDatasource entity) {
        Assert.notNull(entity.getId(), ResultCode.PARAM_NO_ID.getMessage());
        int i = genDatasourceService.deleteById(entity);
        return R.ok(i);
    }

    @GetMapping(Route.DATASOURCE_OPTIONS)
    public R datasourceOptions() {
        List<GenDatasource> list = genDatasourceService.getDatasourceOptions();
        return R.ok(list);
    }



    private void paramCheck(GenDatasource entity) {
        if (entity.getId() == null) {
            entity.setUserCode(SessionHelper.getUserCode());
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
