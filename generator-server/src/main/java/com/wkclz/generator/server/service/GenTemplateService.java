package com.wkclz.generator.server.service;

import com.wkclz.core.base.PageData;
import com.wkclz.generator.server.bean.entity.GenTemplate;
import com.wkclz.generator.server.mapper.GenTemplateMapper;
import com.wkclz.mybatis.helper.PageQuery;
import com.wkclz.mybatis.service.BaseService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenTemplateService extends BaseService<GenTemplate, GenTemplateMapper> {

    public PageData<GenTemplate> getGenTemplatePage(GenTemplate entity) {
        return PageQuery.page(entity, mapper::getTemplateList);
    }

    public List<GenTemplate> getTemplateOpions() {
        return mapper.getTemplateOpions();

    }


    public List<GenTemplate> getTemplates4CodeGen(List<String> tempCodes) {
        if (CollectionUtils.isEmpty(tempCodes)) {
            return null;
        }
        return mapper.getTemplates4CodeGen(tempCodes);
    }

}
