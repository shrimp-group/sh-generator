package com.wkclz.generator.server.rest;

import com.wkclz.core.base.R;
import com.wkclz.core.enums.ResultCode;
import com.wkclz.generator.server.Route;
import com.wkclz.generator.server.bean.entity.GenTask;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Route.PREFIX)
public class GenTaskRest {



    @GetMapping(Route.TASK_LIST)
    public R taskList(GenTask entity) {
        return R.ok();
    }

    @PostMapping(Route.TASK_SAVE)
    public R taskSave(@RequestBody GenTask entity) {
        return R.ok();
    }

    @PostMapping(Route.TASK_REMOVE)
    public R taskRemove(@RequestBody GenTask entity) {
        Assert.notNull(entity.getId(), ResultCode.PARAM_NO_ID.getMessage());
        return R.ok();
    }

}
