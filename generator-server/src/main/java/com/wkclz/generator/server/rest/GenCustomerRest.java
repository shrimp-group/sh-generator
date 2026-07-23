package com.wkclz.generator.server.rest;

import com.wkclz.core.base.R;
import com.wkclz.generator.server.Route;
import com.wkclz.generator.server.bean.dto.GenTaskDto;
import com.wkclz.generator.server.bean.gen.GenParam;
import com.wkclz.generator.server.service.GenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(Route.PREFIX)
@Tag(name = "代码生成", description = "代码生成相关接口")
public class GenCustomerRest {


    @Autowired
    private GenService genService;


    @GetMapping(Route.GEN_DATA)
    @Operation(summary = "获取生成模型数据", description = "根据项目编码获取代码生成所需的模型数据")
    public R<List<GenParam>> genData(
        @Parameter(description = "项目编码", required = true) @PathVariable("projectCode") String projectCode) {
        List<GenParam> genData = genService.getGenData(projectCode);
        return R.ok(genData);
    }

    @GetMapping(Route.GEN_ZIP)
    @Operation(summary = "生成代码压缩包", description = "根据项目编码生成代码并返回 ZIP 压缩文件")
    public void genZip(
        @Parameter(description = "项目编码", required = true) @PathVariable("projectCode") String projectCode,
        HttpServletResponse resp) {
        genService.getGenZip(resp, projectCode);
    }

    @GetMapping(Route.GEN_RULE)
    @Operation(summary = "获取生成规则", description = "根据项目编码获取代码生成规则")
    public R<List<GenTaskDto>> genRule(
        @Parameter(description = "项目编码", required = true) @PathVariable("projectCode") String projectCode) {
        List<GenTaskDto> genRule = genService.getGenRule(projectCode);
        return R.ok(genRule);
    }

}