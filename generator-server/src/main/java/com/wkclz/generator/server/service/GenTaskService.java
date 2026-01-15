package com.wkclz.generator.server.service;

import com.wkclz.generator.server.bean.entity.GenTask;
import com.wkclz.generator.server.mapper.GenTaskMapper;
import com.wkclz.mybatis.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class GenTaskService extends BaseService<GenTask, GenTaskMapper> {

    public List<GenTask> getGenTaskList(String projectCode) {
        Assert.notNull(projectCode, "projectCode 不能为空!");
        GenTask entity = new GenTask();
        entity.setProjectCode(projectCode);
        List<GenTask> taskList = mapper.getTaskList(entity);
        if (CollectionUtils.isEmpty(taskList)) {
            throw new RuntimeException("项目编码错误，项目不存在!");
        }
        return taskList;
    }

    public List<GenTask> getTask4CodeGen(String projectCode) {
        Assert.notNull(projectCode, "projectCode 不能为空!");
        return mapper.getTask4CodeGen(projectCode);
    }


}
