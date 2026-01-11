package com.wkclz.generator.server.rest;

import com.wkclz.core.base.R;
import com.wkclz.core.enums.ResultCode;
import com.wkclz.generator.server.Route;
import com.wkclz.generator.server.bean.entity.GenTemplate;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Route.PREFIX)
public class GenTemplateRest {



    @GetMapping(Route.TEMPLATE_PAGE)
    public R templatePage(GenTemplate entity) {
        return R.ok();
    }

    @GetMapping(Route.TEMPLATE_DETAIL)
    public R templateDetail(GenTemplate entity) {
        Assert.notNull(entity.getId(), ResultCode.PARAM_NO_ID.getMessage());
        return R.ok();
    }

    @PostMapping(Route.TEMPLATE_CREATE)
    public R templateCreate(@RequestBody GenTemplate entity) {
        return R.ok();
    }

    @PostMapping(Route.TEMPLATE_UPDATE)
    public R templateUpdate(@RequestBody GenTemplate entity) {
        Assert.notNull(entity.getId(), ResultCode.PARAM_NO_ID.getMessage());
        Assert.notNull(entity.getId(), ResultCode.UPDATE_NO_VERSION.getMessage());
        return R.ok();
    }

    @PostMapping(Route.TEMPLATE_REMOVE)
    public R templateRemove(@RequestBody GenTemplate entity) {
        Assert.notNull(entity.getId(), ResultCode.PARAM_NO_ID.getMessage());
        return R.ok();
    }

    @GetMapping(Route.TEMPLATE_OPTIONS)
    public R templateOptions() {
        return R.ok();
    }

}
