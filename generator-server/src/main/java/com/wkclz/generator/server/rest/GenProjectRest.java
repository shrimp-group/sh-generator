package com.wkclz.generator.server.rest;

import com.wkclz.core.base.PageData;
import com.wkclz.core.base.R;
import com.wkclz.core.enums.ResultCode;
import com.wkclz.generator.server.Route;
import com.wkclz.generator.server.bean.entity.GenProject;
import com.wkclz.generator.server.service.GenProjectService;
import com.wkclz.iam.sdk.helper.SessionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Route.PREFIX)
public class GenProjectRest {

    @Autowired
    private GenProjectService genProjectService;


    @GetMapping(Route.PROJECT_PAGE)
    public R projectPage(GenProject entity) {
        PageData<GenProject> page = genProjectService.getGenProjectPage(entity);
        return R.ok(page);
    }

    @GetMapping(Route.PROJECT_DETAIL)
    public R projectDetail(GenProject entity) {
        Assert.notNull(entity.getId(), ResultCode.PARAM_NO_ID.getMessage());
        GenProject detail = genProjectService.selectById(entity.getId());
        return R.ok(detail);
    }

    @PostMapping(Route.PROJECT_CREATE)
    public R projectCreate(@RequestBody GenProject entity) {
        paramCheck(entity);
        int insert = genProjectService.insert(entity);
        return R.ok(insert);
    }

    @PostMapping(Route.PROJECT_UPDATE)
    public R projectUpdate(@RequestBody GenProject entity) {
        paramCheck(entity);
        int i = genProjectService.updateById(entity);
        return R.ok(i);
    }

    @PostMapping(Route.PROJECT_REMOVE)
    public R projectRemove(@RequestBody GenProject entity) {
        Assert.notNull(entity.getId(), ResultCode.PARAM_NO_ID.getMessage());
        int i = genProjectService.deleteById(entity);
        return R.ok(i);
    }


    private void paramCheck(GenProject entity) {
        if (entity.getId() == null) {
            entity.setUserCode(SessionHelper.getUserCode());
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
