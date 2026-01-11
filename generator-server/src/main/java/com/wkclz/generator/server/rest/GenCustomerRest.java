package com.wkclz.generator.server.rest;

import com.wkclz.core.base.R;
import com.wkclz.generator.server.Route;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Route.PREFIX)
public class GenCustomerRest {




    @GetMapping(Route.GEN_DATA)
    public R genData(@PathVariable("projectCode") String authCode) {




        return R.ok();
    }

    @GetMapping(Route.GEN_ZIP)
    public R genZip(@PathVariable("projectCode") String authCode) {
        return R.ok();
    }

    @GetMapping(Route.GEN_RULE)
    public R genRule(@PathVariable("projectCode") String authCode) {
        return R.ok();
    }

}
