package com.wkclz.generator.server.mapper;

import com.wkclz.generator.server.bean.entity.GenLog;
import com.wkclz.mybatis.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GenLogMapper extends BaseMapper<GenLog> {

    List<GenLog> getLogList(GenLog entity);

}
