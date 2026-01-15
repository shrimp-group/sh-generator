package com.wkclz.generator.server.rest;

import com.wkclz.core.base.PageData;
import com.wkclz.core.base.R;
import com.wkclz.core.enums.ResultCode;
import com.wkclz.generator.server.Route;
import com.wkclz.generator.server.bean.entity.GenTemplate;
import com.wkclz.generator.server.service.GenTemplateService;
import com.wkclz.iam.sdk.helper.SessionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Route.PREFIX)
public class GenTemplateRest {


    @Autowired
    private GenTemplateService genTemplateService;


    @GetMapping(Route.TEMPLATE_PAGE)
    public R templatePage(GenTemplate entity) {
        PageData<GenTemplate> page = genTemplateService.getGenTemplatePage(entity);
        return R.ok(page);
    }

    @GetMapping(Route.TEMPLATE_DETAIL)
    public R templateDetail(GenTemplate entity) {
        Assert.notNull(entity.getId(), ResultCode.PARAM_NO_ID.getMessage());
        GenTemplate detail = genTemplateService.selectById(entity.getId());
        return R.ok(detail);
    }

    @PostMapping(Route.TEMPLATE_CREATE)
    public R templateCreate(@RequestBody GenTemplate entity) {
        paramCheck(entity);
        int insert = genTemplateService.insert(entity);
        return R.ok(insert);
    }

    @PostMapping(Route.TEMPLATE_UPDATE)
    public R templateUpdate(@RequestBody GenTemplate entity) {
        paramCheck(entity);
        int i = genTemplateService.updateById(entity);
        return R.ok(i);
    }

    @PostMapping(Route.TEMPLATE_REMOVE)
    public R templateRemove(@RequestBody GenTemplate entity) {
        Assert.notNull(entity.getId(), ResultCode.PARAM_NO_ID.getMessage());
        int i = genTemplateService.deleteById(entity);
        return R.ok(i);
    }

    @GetMapping(Route.TEMPLATE_OPTIONS)
    public R templateOptions() {
        List<GenTemplate> list = genTemplateService.getTemplateOpions();
        return R.ok(list);
    }


    private void paramCheck(GenTemplate entity) {
        if (entity.getId() == null) {
            entity.setUserCode(SessionHelper.getUserCode());
        } else {
            Assert.notNull(entity.getTempCode(), "tempCode 不能为空");
            Assert.notNull(entity.getId(), ResultCode.PARAM_NO_ID.getMessage());
            Assert.notNull(entity.getId(), ResultCode.UPDATE_NO_VERSION.getMessage());
        }
        Assert.notNull(entity.getTempName(), "tempName 不能为空");
        Assert.notNull(entity.getTempSubfix(), "tempSubfix 不能为空");
        Assert.notNull(entity.getTempContent(), "tempContent 不能为空");
    }


}
