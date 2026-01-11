package com.wkclz.generator.server.mapper;

import com.wkclz.generator.server.bean.entity.GenProject;
import com.wkclz.mybatis.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GenProjectMapper extends BaseMapper<GenProject> {

    List<GenProject> getProjectList(GenProject entity);

}
