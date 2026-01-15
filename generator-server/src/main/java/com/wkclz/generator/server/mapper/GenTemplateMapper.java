package com.wkclz.generator.server.mapper;

import com.wkclz.generator.server.bean.entity.GenTemplate;
import com.wkclz.mybatis.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GenTemplateMapper extends BaseMapper<GenTemplate> {

    List<GenTemplate> getTemplateList(GenTemplate entity);
    List<GenTemplate> getTemplateOpions();

    List<GenTemplate> getTemplates4CodeGen(@Param("tempCodes") List<String> tempCodes);

}
