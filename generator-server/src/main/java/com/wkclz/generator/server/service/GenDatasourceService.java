package com.wkclz.generator.server.service;

import com.wkclz.core.base.PageData;
import com.wkclz.generator.server.bean.entity.GenDatasource;
import com.wkclz.generator.server.mapper.GenDatasourceMapper;
import com.wkclz.mybatis.helper.PageQuery;
import com.wkclz.mybatis.service.BaseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class GenDatasourceService extends BaseService<GenDatasource, GenDatasourceMapper> {

    public PageData<GenDatasource> getGenDatasourcePage(GenDatasource entity) {
        return PageQuery.page(entity, mapper::getDatasourceList);
    }

    public List<GenDatasource> getDatasourceOptions() {
        return mapper.getDatasourceOptions();
    }

    public int create(GenDatasource entity) {
        return insert(entity);
    }

    public int update(GenDatasource entity) {
        GenDatasource exist = selectById(entity.getId());
        Assert.notNull(exist, "数据源不存在");

        BeanUtils.copyProperties(entity, exist, "dbPassword");
        if (StringUtils.isEmpty(entity.getDbPassword())) {
            exist.setDbPassword(exist.getDbPassword());
        }
        return updateByIdSelective(exist);
    }

}
