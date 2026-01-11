package com.wkclz.generator.server.service;

import com.wkclz.core.base.PageData;
import com.wkclz.generator.server.bean.entity.GenProject;
import com.wkclz.generator.server.mapper.GenProjectMapper;
import com.wkclz.mybatis.helper.PageQuery;
import com.wkclz.mybatis.service.BaseService;
import org.springframework.stereotype.Service;

@Service
public class GenProjectService extends BaseService<GenProject, GenProjectMapper> {

    public PageData<GenProject> getGenProjectPage(GenProject entity) {
        return PageQuery.page(entity, mapper::getProjectList);
    }

}
