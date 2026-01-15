package com.wkclz.generator.server.rest;

import com.wkclz.core.base.PageData;
import com.wkclz.core.base.R;
import com.wkclz.core.enums.ResultCode;
import com.wkclz.generator.server.Route;
import com.wkclz.generator.server.bean.entity.GenLog;
import com.wkclz.generator.server.service.GenLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Route.PREFIX)
public class GenLogRest {

    @Autowired
    private GenLogService genLogService;


    @GetMapping(Route.LOG_PAGE)
    public R logPage(GenLog entity) {
        PageData<GenLog> page = genLogService.getGenLogPage(entity);
        return R.ok(page);
    }

    @GetMapping(Route.LOG_DETAIL)
    public R logDetail(GenLog entity) {
        Assert.notNull(entity.getId(), ResultCode.PARAM_NO_ID.getMessage());
        GenLog detail = genLogService.selectById(entity.getId());
        return R.ok(detail);
    }

}
