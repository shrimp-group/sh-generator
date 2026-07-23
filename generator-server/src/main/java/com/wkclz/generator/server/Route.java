package com.wkclz.generator.server;

import com.wkclz.core.annotation.Router;

@Router(module = "generator", prefix = Route.PREFIX)
public interface Route {

    String PREFIX = "/generator";



    // 数据源
    String DATASOURCE_PAGE = "/datasource/page";
    String DATASOURCE_DETAIL = "/datasource/detail";
    String DATASOURCE_CREATE = "/datasource/create";
    String DATASOURCE_UPDATE = "/datasource/update";
    String DATASOURCE_REMOVE = "/datasource/remove";
    String DATASOURCE_OPTIONS = "/datasource/options";

    // 模板
    String TEMPLATE_PAGE = "/template/page";
    String TEMPLATE_DETAIL = "/template/detail";
    String TEMPLATE_CREATE = "/template/create";
    String TEMPLATE_UPDATE = "/template/update";
    String TEMPLATE_REMOVE = "/template/remove";
    String TEMPLATE_OPTIONS = "/template/options";

    // 项目
    String PROJECT_PAGE = "/project/page";
    String PROJECT_DETAIL = "/project/detail";
    String PROJECT_CREATE = "/project/create";
    String PROJECT_UPDATE = "/project/update";
    String PROJECT_REMOVE = "/project/remove";
    String PROJECT_COPY = "/project/copy";

    // 任务
    String TASK_LIST = "/task/list";
    String TASK_SAVE = "/task/save";
    String TASK_REMOVE = "/task/remove";

    // 日志
    String LOG_PAGE = "/log/page";
    String LOG_DETAIL = "/log/detail";








    // 代码生成


    String GEN_DATA = "/public/gen/data/{projectCode}";
    String GEN_ZIP = "/public/gen/zip/{projectCode}";
    String GEN_RULE = "/public/gen/rule/{projectCode}";



}
