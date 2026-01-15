package com.wkclz.generator.server.rest;

import com.wkclz.core.base.R;
import com.wkclz.core.enums.ResultCode;
import com.wkclz.generator.server.Route;
import com.wkclz.generator.server.bean.entity.GenTask;
import com.wkclz.generator.server.service.GenTaskService;
import com.wkclz.iam.sdk.helper.SessionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Route.PREFIX)
public class GenTaskRest {

    @Autowired
    private GenTaskService genTaskService;


    @GetMapping(Route.TASK_LIST)
    public R taskList(GenTask entity) {
        Assert.notNull(entity.getProjectCode(), "projectCode 不能为空!");
        List<GenTask> list = genTaskService.getGenTaskList(entity.getProjectCode());
        return R.ok(list);
    }

    @PostMapping(Route.TASK_SAVE)
    public R taskSave(@RequestBody GenTask entity) {
        paramCheck(entity);
        int insert = genTaskService.insert(entity);
        return R.ok(insert);
    }

    @PostMapping(Route.TASK_REMOVE)
    public R taskRemove(@RequestBody GenTask entity) {
        Assert.notNull(entity.getId(), ResultCode.PARAM_NO_ID.getMessage());
        int i = genTaskService.deleteById(entity);
        return R.ok(i);
    }


    private void paramCheck(GenTask entity) {
        if (entity.getId() == null) {
            entity.setUserCode(SessionHelper.getUserCode());
        } else {
            Assert.notNull(entity.getId(), ResultCode.PARAM_NO_ID.getMessage());
            Assert.notNull(entity.getId(), ResultCode.UPDATE_NO_VERSION.getMessage());
        }
        Assert.notNull(entity.getTaskName(), "taskName 不能为空");
        Assert.notNull(entity.getProjectCode(), "projectCode 不能为空");
        Assert.notNull(entity.getTempCode(), "tempCode 不能为空");
    }


}
