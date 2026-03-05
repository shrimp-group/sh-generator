package com.wkclz.generator.server.service;

import com.wkclz.generator.server.bean.dto.GenTaskDto;
import com.wkclz.generator.server.bean.entity.GenTask;
import com.wkclz.generator.server.mapper.GenTaskMapper;
import com.wkclz.mybatis.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class GenTaskService extends BaseService<GenTask, GenTaskMapper> {

    public List<GenTask> getGenTaskList(String projectCode) {
        Assert.notNull(projectCode, "projectCode 不能为空!");
        GenTask entity = new GenTask();
        entity.setProjectCode(projectCode);
        return mapper.getTaskList(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer taskSave(List<GenTask> tasks) {
        // 当前存在的所有任务
        GenTask param = new GenTask();
        param.setProjectCode(tasks.get(0).getProjectCode());
        List<GenTask> savedTasks = mapper.selectByEntity(param);
        if (savedTasks == null) {
            savedTasks = new ArrayList<>();
        }

        // 新增的 tasks
        List<String> existTempCodes = savedTasks.stream().map(GenTask::getTempCode).toList();
        List<GenTask> inserts = tasks.stream().filter(t -> !existTempCodes.contains(t.getTempCode())).toList();

        // 删除的 tasks
        List<String> frontTempCodes = tasks.stream().map(GenTask::getTempCode).toList();
        List<GenTask> deletes = savedTasks.stream().filter(t -> !frontTempCodes.contains(t.getTempCode())).toList();


        // 修改的 tasks
        List<GenTask> updates = new ArrayList<>();
        for (GenTask task : tasks) {
            for (GenTask savedTask : savedTasks) {
                if (task.getTempCode().equals(savedTask.getTempCode())) {
                    boolean isUpdate = false;
                    if (Objects.equals(task.getTaskName(), savedTask.getTaskName())) {
                        isUpdate = true;
                        savedTask.setTaskName(task.getTaskName());
                    }
                    if (Objects.equals(task.getProjectBasePath(), savedTask.getProjectBasePath())) {
                        isUpdate = true;
                        savedTask.setProjectBasePath(task.getProjectBasePath());
                    }
                    if (Objects.equals(task.getPackagePath(), savedTask.getPackagePath())) {
                        isUpdate = true;
                        savedTask.setPackagePath(task.getPackagePath());
                    }
                    if (Objects.equals(task.getTaskDesc(), savedTask.getTaskDesc())) {
                        isUpdate = true;
                        savedTask.setTaskDesc(task.getTaskDesc());
                    }
                    if (Objects.equals(task.getCreateSwitch(), savedTask.getCreateSwitch())) {
                        isUpdate = true;
                        savedTask.setCreateSwitch(task.getCreateSwitch());
                    }
                    if (Objects.equals(task.getDeleteSwitch(), savedTask.getDeleteSwitch())) {
                        isUpdate = true;
                        savedTask.setDeleteSwitch(task.getDeleteSwitch());
                    }
                    if (Objects.equals(task.getRemark(), savedTask.getRemark())) {
                        isUpdate = true;
                        savedTask.setRemark(task.getRemark());
                    }
                    if (isUpdate) {
                        updates.add(savedTask);
                    }
                }
            }
        }


        if (!CollectionUtils.isEmpty(inserts)) {
            mapper.insertBatch(inserts);
        }
        if (!CollectionUtils.isEmpty(updates)) {
            for (GenTask update : updates) {
                updateById(update);
            }
        }
        if (!CollectionUtils.isEmpty(deletes)) {
            for (GenTask delete : deletes) {
                mapper.deleteById(delete);
            }
        }
        return tasks.size();
    }

    public List<GenTaskDto> getTask4CodeGen(String projectCode) {
        Assert.notNull(projectCode, "projectCode 不能为空!");
        return mapper.getTask4CodeGen(projectCode);
    }


}
