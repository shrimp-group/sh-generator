package com.wkclz.generator.server.rest;

import com.wkclz.core.base.R;
import com.wkclz.core.enums.ResultCode;
import com.wkclz.generator.server.Route;
import com.wkclz.generator.server.bean.entity.GenProject;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Route.PREFIX)
public class GenProjectRest {



    @GetMapping(Route.PROJECT_PAGE)
    public R projectPage(GenProject entity) {
        return R.ok();
    }

    @GetMapping(Route.PROJECT_DETAIL)
    public R projectDetail(GenProject entity) {
        Assert.notNull(entity.getId(), ResultCode.PARAM_NO_ID.getMessage());
        return R.ok();
    }

    @PostMapping(Route.PROJECT_CREATE)
    public R projectCreate(@RequestBody GenProject entity) {
        return R.ok();
    }

    @PostMapping(Route.PROJECT_UPDATE)
    public R projectUpdate(@RequestBody GenProject entity) {
        Assert.notNull(entity.getId(), ResultCode.PARAM_NO_ID.getMessage());
        Assert.notNull(entity.getId(), ResultCode.UPDATE_NO_VERSION.getMessage());
        return R.ok();
    }

    @PostMapping(Route.PROJECT_REMOVE)
    public R projectRemove(@RequestBody GenProject entity) {
        Assert.notNull(entity.getId(), ResultCode.PARAM_NO_ID.getMessage());
        return R.ok();
    }

}
