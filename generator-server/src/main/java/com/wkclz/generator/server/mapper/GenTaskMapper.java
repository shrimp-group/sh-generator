package com.wkclz.generator.server.mapper;

import com.wkclz.generator.server.bean.entity.GenTask;
import com.wkclz.mybatis.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GenTaskMapper extends BaseMapper<GenTask> {

    List<GenTask> getTaskList(GenTask entity);

    List<GenTask> getTask4CodeGen(@Param("projectCode") String projectCode);


}
