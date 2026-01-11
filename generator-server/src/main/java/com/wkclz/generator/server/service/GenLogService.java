package com.wkclz.generator.server.service;

import com.wkclz.core.base.PageData;
import com.wkclz.generator.server.bean.entity.GenLog;
import com.wkclz.generator.server.mapper.GenLogMapper;
import com.wkclz.mybatis.helper.PageQuery;
import com.wkclz.mybatis.service.BaseService;
import org.springframework.stereotype.Service;

@Service
public class GenLogService extends BaseService<GenLog, GenLogMapper> {

    public PageData<GenLog> getGenLogPage(GenLog entity) {
        return PageQuery.page(entity, mapper::getLogList);
    }

}
