package com.wkclz.generator.server.rest;

import com.wkclz.core.base.PageData;
import com.wkclz.core.base.R;
import com.wkclz.core.enums.ResultCode;
import com.wkclz.core.identity.IdentityContext;
import com.wkclz.generator.server.Route;
import com.wkclz.generator.server.bean.entity.GenProject;
import com.wkclz.generator.server.service.GenProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Route.PREFIX)
@Tag(name = "项目管理", description = "代码生成项目的增删改查操作")
public class GenProjectRest {

    @Autowired
    private GenProjectService genProjectService;


    @GetMapping(Route.PROJECT_PAGE)
    @Operation(summary = "项目分页查询", description = "根据条件分页查询项目列表")
    public R<PageData<GenProject>> projectPage(
        @Parameter(description = "项目名称") GenProject entity) {
        PageData<GenProject> page = genProjectService.getGenProjectPage(entity);
        return R.ok(page);
    }

    @GetMapping(Route.PROJECT_DETAIL)
    @Operation(summary = "项目详情查询", description = "根据ID查询项目详情")
    public R<GenProject> projectDetail(
        @Parameter(description = "项目ID", required = true) GenProject entity) {
        Assert.notNull(entity.getId(), ResultCode.PARAM_NO_ID.getMessage());
        GenProject detail = genProjectService.selectById(entity.getId());
        return R.ok(detail);
    }

    @PostMapping(Route.PROJECT_CREATE)
    @Operation(summary = "新增项目", description = "创建新的代码生成项目")
    public R<GenProject> projectCreate(
        @Parameter(description = "项目信息", required = true) @RequestBody GenProject entity) {
        paramCheck(entity);
        GenProject insert = genProjectService.create(entity);
        return R.ok(insert);
    }

    @PostMapping(Route.PROJECT_UPDATE)
    @Operation(summary = "更新项目", description = "更新项目信息")
    public R<GenProject> projectUpdate(
        @Parameter(description = "项目信息", required = true) @RequestBody GenProject entity) {
        paramCheck(entity);
        GenProject project = genProjectService.update(entity);
        return R.ok(project);
    }

    @PostMapping(Route.PROJECT_REMOVE)
    @Operation(summary = "删除项目", description = "根据ID删除项目")
    public R<Integer> projectRemove(
        @Parameter(description = "项目信息", required = true) @RequestBody GenProject entity) {
        Assert.notNull(entity.getId(), ResultCode.PARAM_NO_ID.getMessage());
        int i = genProjectService.deleteById(entity);
        return R.ok(i);
    }

    @PostMapping(Route.PROJECT_COPY)
    @Operation(summary = "复制项目", description = "根据ID复制项目")
    public R<GenProject> projectCopy(
        @Parameter(description = "项目信息", required = true) @RequestBody GenProject entity) {
        Assert.notNull(entity.getId(), ResultCode.PARAM_NO_ID.getMessage());
        GenProject project = genProjectService.projectCopy(entity.getId());
        return R.ok(project);
    }


    private void paramCheck(GenProject entity) {
        if (entity.getId() == null) {
            entity.setUserCode(IdentityContext.getUserCode());
        } else {
            Assert.notNull(entity.getProjectCode(), "projectCode 不能为空");
            Assert.notNull(entity.getId(), ResultCode.PARAM_NO_ID.getMessage());
            Assert.notNull(entity.getId(), ResultCode.UPDATE_NO_VERSION.getMessage());
        }
        Assert.notNull(entity.getDbCode(), "dbCode 不能为空");
        Assert.notNull(entity.getModuleName(), "moduleName 不能为空");
        Assert.notNull(entity.getProjectName(), "projectName 不能为空");
    }


}
