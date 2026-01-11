package com.wkclz.generator.server.service;

import com.wkclz.core.base.PageData;
import com.wkclz.generator.server.bean.entity.GenTask;
import com.wkclz.generator.server.mapper.GenTaskMapper;
import com.wkclz.mybatis.helper.PageQuery;
import com.wkclz.mybatis.service.BaseService;
import org.springframework.stereotype.Service;

@Service
public class GenTaskService extends BaseService<GenTask, GenTaskMapper> {

    public PageData<GenTask> getGenTaskPage(GenTask entity) {
        return PageQuery.page(entity, mapper::getTaskList);
    }

}
