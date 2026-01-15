package com.wkclz.generator.server.mapper;

import com.wkclz.generator.server.bean.entity.GenDatasource;
import com.wkclz.mybatis.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GenDatasourceMapper extends BaseMapper<GenDatasource> {

    List<GenDatasource> getDatasourceList(GenDatasource entity);

    List<GenDatasource> getDatasourceOptions();

}
