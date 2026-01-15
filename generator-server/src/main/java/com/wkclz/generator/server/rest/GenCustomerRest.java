package com.wkclz.generator.server.rest;

import com.wkclz.core.base.R;
import com.wkclz.generator.server.Route;
import com.wkclz.generator.server.bean.entity.GenTask;
import com.wkclz.generator.server.bean.gen.GenParam;
import com.wkclz.generator.server.service.GenService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(Route.PREFIX)
public class GenCustomerRest {


    @Autowired
    private GenService genService;


    @GetMapping(Route.GEN_DATA)
    public R genData(@PathVariable("projectCode") String projectCode) {
        List<GenParam> genData = genService.getGenData(projectCode);
        return R.ok(genData);
    }

    @GetMapping(Route.GEN_ZIP)
    public void genZip(@PathVariable("projectCode") String projectCode, HttpServletResponse resp) {
        genService.getGenZip(resp, projectCode);
    }

    @GetMapping(Route.GEN_RULE)
    public R genRule(@PathVariable("projectCode") String projectCode) {
        List<GenTask> genRule = genService.getGenRule(projectCode);
        return R.ok(genRule);
    }

}
