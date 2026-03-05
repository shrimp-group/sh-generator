package com.wkclz.generator.server.service;

import com.wkclz.core.base.PageData;
import com.wkclz.core.enums.ResultCode;
import com.wkclz.core.exception.UserException;
import com.wkclz.core.exception.ValidationException;
import com.wkclz.generator.server.bean.entity.GenProject;
import com.wkclz.generator.server.bean.entity.GenTask;
import com.wkclz.generator.server.mapper.GenProjectMapper;
import com.wkclz.generator.server.mapper.GenTaskMapper;
import com.wkclz.mybatis.helper.PageQuery;
import com.wkclz.mybatis.service.BaseService;
import com.wkclz.redis.helper.RedisIdGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class GenProjectService extends BaseService<GenProject, GenProjectMapper> {


    @Autowired
    private GenTaskMapper genTaskMapper;
    @Autowired
    private RedisIdGenerator redisIdGenerator;

    public PageData<GenProject> getGenProjectPage(GenProject entity) {
        return PageQuery.page(entity, mapper::getProjectList);
    }


    public GenProject create(GenProject entity) {
        duplicateCheck(entity);
        if (StringUtils.isBlank(entity.getProjectCode())) {
            entity.setProjectCode(redisIdGenerator.generateIdWithPrefix("gen_"));
        }
        mapper.insert(entity);
        return entity;
    }

    public GenProject update(GenProject entity) {
        duplicateCheck(entity);
        GenProject oldEntity = selectById(entity.getId());
        if (oldEntity == null) {
            throw ValidationException.of(ResultCode.RECORD_NOT_EXIST);
        }
        if (StringUtils.isBlank(entity.getProjectCode())) {
            entity.setProjectCode(oldEntity.getProjectCode());
        } else {
            // 更换 projectCode
            GenTask taskParam = new GenTask();
            taskParam.setProjectCode(oldEntity.getProjectCode());
            List<GenTask> genTasks = genTaskMapper.selectByEntity(taskParam);
            if (!CollectionUtils.isEmpty(genTasks)) {
                for (GenTask genTask : genTasks) {
                    genTask.setProjectCode(entity.getProjectCode());
                    genTaskMapper.updateById(genTask);
                }
            }
        }
        GenProject.copyIfNotNull(entity, oldEntity);
        updateByIdSelective(oldEntity);
        return oldEntity;
    }



    public GenProject projectCopy(Long id) {
        GenProject oldEntity = selectById(id);
        if (oldEntity == null) {
            throw ValidationException.of("id 错误,项目不存在");
        }
        String newProjectCode = redisIdGenerator.generateIdWithPrefix("gen_");

        GenTask taskParam = new GenTask();
        taskParam.setProjectCode(oldEntity.getProjectCode());
        List<GenTask> genTasks = genTaskMapper.selectByEntity(taskParam);

        if (!CollectionUtils.isEmpty(genTasks)) {
            for (GenTask genTask : genTasks) {
                genTask.setProjectCode(newProjectCode);
                genTaskMapper.insert(genTask);
            }
        }
        oldEntity.setProjectCode(newProjectCode);
        oldEntity.setProjectName("复制从:" + oldEntity.getProjectName());
        insert(oldEntity);
        return oldEntity;
    }




    public GenProject getProjectByCode(String projectCode) {
        Assert.notNull(projectCode, "projectCode 不能为空");
        GenProject project = new GenProject();
        project.setProjectCode(projectCode);
        project = mapper.selectOneByEntity(project);

        if (project == null) {
            throw ValidationException.of("项目编码错误，项目不存在");
        }
        return project;
    }



    private void duplicateCheck(GenProject entity) {
        // 唯一条件为空，直接通过
        if (StringUtils.isBlank(entity.getProjectCode())) {
            return;
        }

        // 唯一条件不为空，请设置唯一条件
        GenProject param = new GenProject();
        // 唯一条件
        param.setProjectCode(entity.getProjectCode());
        param = selectOneByEntity(param);
        if (param == null) {
            return;
        }
        if (param.getId().equals(entity.getId())) {
            return;
        }
        // 查到有值，为新增或 id 不一样场景，为数据重复
        throw UserException.of(ResultCode.RECORD_DUPLICATE);
    }

}
