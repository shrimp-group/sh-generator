package com.wkclz.generator.server.rest;

import com.wkclz.core.base.R;
import com.wkclz.core.enums.ResultCode;
import com.wkclz.generator.server.Route;
import com.wkclz.generator.server.bean.entity.GenLog;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Route.PREFIX)
public class GenLogRest {



    @GetMapping(Route.LOG_PAGE)
    public R logPage(GenLog entity) {
        return R.ok();
    }

    @GetMapping(Route.LOG_DETAIL)
    public R logDetail(GenLog entity) {
        Assert.notNull(entity.getId(), ResultCode.PARAM_NO_ID.getMessage());
        return R.ok();
    }

}
