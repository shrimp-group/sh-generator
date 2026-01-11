package com.wkclz.generator.server.service;

import com.wkclz.core.base.PageData;
import com.wkclz.generator.server.bean.entity.GenDatasource;
import com.wkclz.generator.server.mapper.GenDatasourceMapper;
import com.wkclz.mybatis.helper.PageQuery;
import com.wkclz.mybatis.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenDatasourceService extends BaseService<GenDatasource, GenDatasourceMapper> {

    public PageData<GenDatasource> getGenDatasourcePage(GenDatasource entity) {
        return PageQuery.page(entity, mapper::getDatasourceList);
    }

    public List<GenDatasource> getDatasourceOptions() {
        return mapper.getDatasourceOptions();

    }

}
