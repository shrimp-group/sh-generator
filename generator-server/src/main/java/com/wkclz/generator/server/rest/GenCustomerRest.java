package com.wkclz.generator.server.rest;

import com.wkclz.core.base.R;
import com.wkclz.generator.server.Route;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Route.PREFIX)
public class GenCustomerRest {



    @GetMapping(Route.GEN_ZIP)
    public R genZip(@PathVariable("authCode") String authCode) {
        return R.ok();
    }

    @GetMapping(Route.GEN_RULE)
    public R genRule(@PathVariable("authCode") String authCode) {
        return R.ok();
    }

}
