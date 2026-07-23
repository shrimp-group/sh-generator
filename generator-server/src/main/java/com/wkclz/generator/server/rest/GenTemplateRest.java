package com.wkclz.generator.server.rest;

import com.wkclz.core.base.PageData;
import com.wkclz.core.base.R;
import com.wkclz.core.enums.ResultCode;
import com.wkclz.core.identity.IdentityContext;
import com.wkclz.generator.server.Route;
import com.wkclz.generator.server.bean.entity.GenTemplate;
import com.wkclz.generator.server.service.GenTemplateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Route.PREFIX)
@Tag(name = "模板管理", description = "代码生成模板的增删改查操作")
public class GenTemplateRest {


    @Autowired
    private GenTemplateService genTemplateService;


    @GetMapping(Route.TEMPLATE_PAGE)
    @Operation(summary = "模板分页查询", description = "根据条件分页查询模板列表")
    public R<PageData<GenTemplate>> templatePage(
        @Parameter(description = "模板信息") GenTemplate entity) {
        PageData<GenTemplate> page = genTemplateService.getGenTemplatePage(entity);
        return R.ok(page);
    }

    @GetMapping(Route.TEMPLATE_DETAIL)
    @Operation(summary = "模板详情查询", description = "根据ID查询模板详情")
    public R<GenTemplate> templateDetail(
        @Parameter(description = "模板ID", required = true) GenTemplate entity) {
        Assert.notNull(entity.getId(), ResultCode.PARAM_NO_ID.getMessage());
        GenTemplate detail = genTemplateService.selectById(entity.getId());
        return R.ok(detail);
    }

    @PostMapping(Route.TEMPLATE_CREATE)
    @Operation(summary = "新增模板", description = "创建新的代码生成模板")
    public R<Integer> templateCreate(
        @Parameter(description = "模板信息", required = true) @RequestBody GenTemplate entity) {
        paramCheck(entity);
        int insert = genTemplateService.insert(entity);
        return R.ok(insert);
    }

    @PostMapping(Route.TEMPLATE_UPDATE)
    @Operation(summary = "更新模板", description = "更新模板信息")
    public R<Integer> templateUpdate(
        @Parameter(description = "模板信息", required = true) @RequestBody GenTemplate entity) {
        paramCheck(entity);
        int i = genTemplateService.updateById(entity);
        return R.ok(i);
    }

    @PostMapping(Route.TEMPLATE_REMOVE)
    @Operation(summary = "删除模板", description = "根据ID删除模板")
    public R<Integer> templateRemove(
        @Parameter(description = "模板信息", required = true) @RequestBody GenTemplate entity) {
        Assert.notNull(entity.getId(), ResultCode.PARAM_NO_ID.getMessage());
        int i = genTemplateService.deleteById(entity);
        return R.ok(i);
    }

    @GetMapping(Route.TEMPLATE_OPTIONS)
    @Operation(summary = "模板选项查询", description = "获取所有模板选项列表")
    public R<List<GenTemplate>> templateOptions() {
        List<GenTemplate> list = genTemplateService.getTemplateOpions();
        return R.ok(list);
    }


    private void paramCheck(GenTemplate entity) {
        if (entity.getId() == null) {
            entity.setUserCode(IdentityContext.getUserCode());
        } else {
            Assert.notNull(entity.getTempCode(), "tempCode 不能为空");
            Assert.notNull(entity.getId(), ResultCode.PARAM_NO_ID.getMessage());
            Assert.notNull(entity.getId(), ResultCode.UPDATE_NO_VERSION.getMessage());
        }
        Assert.notNull(entity.getTempKey(), "tempKey 不能为空");
        Assert.notNull(entity.getTempName(), "tempName 不能为空");
        Assert.notNull(entity.getTempSubfix(), "tempSubfix 不能为空");
        Assert.notNull(entity.getTempContent(), "tempContent 不能为空");
    }


}
