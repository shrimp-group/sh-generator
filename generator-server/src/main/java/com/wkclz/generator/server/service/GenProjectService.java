package com.wkclz.generator.server.service;

import com.wkclz.core.base.PageData;
import com.wkclz.core.exception.ValidationException;
import com.wkclz.generator.server.bean.entity.GenProject;
import com.wkclz.generator.server.mapper.GenProjectMapper;
import com.wkclz.mybatis.helper.PageQuery;
import com.wkclz.mybatis.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class GenProjectService extends BaseService<GenProject, GenProjectMapper> {

    public PageData<GenProject> getGenProjectPage(GenProject entity) {
        return PageQuery.page(entity, mapper::getProjectList);
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


}
