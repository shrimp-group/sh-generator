package com.wkclz.generator.server.rest;

import com.wkclz.core.base.R;
import com.wkclz.core.enums.ResultCode;
import com.wkclz.generator.server.Route;
import com.wkclz.generator.server.bean.entity.GenDatasource;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Route.PREFIX)
public class GenDatasourceRest {



    @GetMapping(Route.DATASOURCE_PAGE)
    public R datasourcePage(GenDatasource entity) {
        return R.ok();
    }

    @GetMapping(Route.DATASOURCE_DETAIL)
    public R datasourceDetail(GenDatasource entity) {
        Assert.notNull(entity.getId(), ResultCode.PARAM_NO_ID.getMessage());
        return R.ok();
    }

    @PostMapping(Route.DATASOURCE_CREATE)
    public R datasourceCreate(@RequestBody GenDatasource entity) {
        return R.ok();
    }

    @PostMapping(Route.DATASOURCE_UPDATE)
    public R datasourceUpdate(@RequestBody GenDatasource entity) {
        Assert.notNull(entity.getId(), ResultCode.PARAM_NO_ID.getMessage());
        Assert.notNull(entity.getId(), ResultCode.UPDATE_NO_VERSION.getMessage());
        return R.ok();
    }

    @PostMapping(Route.DATASOURCE_REMOVE)
    public R datasourceRemove(@RequestBody GenDatasource entity) {
        Assert.notNull(entity.getId(), ResultCode.PARAM_NO_ID.getMessage());
        return R.ok();
    }

    @GetMapping(Route.DATASOURCE_OPTIONS)
    public R datasourceOptions() {
        return R.ok();
    }

}
