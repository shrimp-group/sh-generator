package com.wkclz.generator.server.rest;

import com.wkclz.core.base.R;
import com.wkclz.core.enums.ResultCode;
import com.wkclz.core.exception.ValidationException;
import com.wkclz.core.identity.IdentityContext;
import com.wkclz.generator.server.Route;
import com.wkclz.generator.server.bean.entity.GenTask;
import com.wkclz.generator.server.service.GenTaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Route.PREFIX)
@Tag(name = "任务管理", description = "代码生成任务的增删改查操作")
public class GenTaskRest {

    @Autowired
    private GenTaskService genTaskService;


    @GetMapping(Route.TASK_LIST)
    @Operation(summary = "任务列表查询", description = "根据项目编码查询任务列表")
    public R<List<GenTask>> taskList(
        @Parameter(description = "任务信息", required = true) GenTask entity) {
        Assert.notNull(entity.getProjectCode(), "projectCode 不能为空!");
        List<GenTask> list = genTaskService.getGenTaskList(entity.getProjectCode());
        return R.ok(list);
    }

    @PostMapping(Route.TASK_SAVE)
    @Operation(summary = "保存任务", description = "批量保存任务列表")
    public R<Integer> taskSave(
        @Parameter(description = "任务列表", required = true) @RequestBody List<GenTask> entitys) {
        paramCheck(entitys);
        int insert = genTaskService.taskSave(entitys);
        return R.ok(insert);
    }

    @PostMapping(Route.TASK_REMOVE)
    @Operation(summary = "删除任务", description = "根据ID删除任务")
    public R<Integer> taskRemove(
        @Parameter(description = "任务信息", required = true) @RequestBody GenTask entity) {
        Assert.notNull(entity.getId(), ResultCode.PARAM_NO_ID.getMessage());
        int i = genTaskService.deleteById(entity);
        return R.ok(i);
    }


    private void paramCheck(List<GenTask> entitys) {
        if (CollectionUtils.isEmpty(entitys)) {
            throw ValidationException.of("任务列表不能为空!");
        }
        for (GenTask entity : entitys) {
            if (entity.getId() == null) {
                entity.setUserCode(IdentityContext.getUserCode());
            } else {
                Assert.notNull(entity.getId(), ResultCode.PARAM_NO_ID.getMessage());
                Assert.notNull(entity.getId(), ResultCode.UPDATE_NO_VERSION.getMessage());
            }
            Assert.notNull(entity.getProjectCode(), "projectCode 不能为空");
            Assert.notNull(entity.getTaskName(), "taskName 不能为空");
            Assert.notNull(entity.getTempCode(), "tempCode 不能为空");
        }
        long projectCount = entitys.stream().map(GenTask::getProjectCode).distinct().count();
        if (projectCount > 1) {
            throw ValidationException.of("一次只能操作一个项目!");
        }

        long templateCount = entitys.stream().map(GenTask::getTempCode).distinct().count();
        if (templateCount != entitys.size()) {
            throw ValidationException.of("每个模板只能存在一个任务中!");
        }
    }


}
