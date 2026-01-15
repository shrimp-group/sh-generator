package com.wkclz.generator.server;

import com.wkclz.core.annotation.Desc;
import com.wkclz.core.annotation.Router;

@Router(module = "generator", prefix = Route.PREFIX)
public interface Route {

    String PREFIX = "/generator";



    // 数据源
    @Desc("数据源-分页")
    String DATASOURCE_PAGE = "/datasource/page";
    @Desc("数据源-详情")
    String DATASOURCE_DETAIL = "/datasource/detail";
    @Desc("数据源-新增")
    String DATASOURCE_CREATE = "/datasource/create";
    @Desc("数据源-修改")
    String DATASOURCE_UPDATE = "/datasource/update";
    @Desc("数据源-删除")
    String DATASOURCE_REMOVE = "/datasource/remove";
    @Desc("数据源-选项")
    String DATASOURCE_OPTIONS = "/datasource/options";

    // 模板
    @Desc("代码模板-分页")
    String TEMPLATE_PAGE = "/template/page";
    @Desc("代码模板-详情")
    String TEMPLATE_DETAIL = "/template/detail";
    @Desc("代码模板-新增")
    String TEMPLATE_CREATE = "/template/create";
    @Desc("代码模板-修改")
    String TEMPLATE_UPDATE = "/template/update";
    @Desc("代码模板-删除")
    String TEMPLATE_REMOVE = "/template/remove";
    @Desc("代码模板-选项")
    String TEMPLATE_OPTIONS = "/template/options";

    // 项目
    @Desc("项目-分页")
    String PROJECT_PAGE = "/project/page";
    @Desc("项目-详情")
    String PROJECT_DETAIL = "/project/detail";
    @Desc("项目-新增")
    String PROJECT_CREATE = "/project/create";
    @Desc("项目-修改")
    String PROJECT_UPDATE = "/project/update";
    @Desc("项目-删除")
    String PROJECT_REMOVE = "/project/remove";

    // 任务
    @Desc("任务-分页")
    String TASK_LIST = "/task/list";
    @Desc("任务-保存")
    String TASK_SAVE = "/task/save";
    @Desc("任务-删除")
    String TASK_REMOVE = "/task/remove";

    // 日志
    @Desc("日志-分页")
    String LOG_PAGE = "/log/page";
    @Desc("日志-详情")
    String LOG_DETAIL = "/log/detail";








    // 代码生成


    @Desc("代码生成-模型数据")
    String GEN_DATA = "/public/gen/data/{projectCode}";
    @Desc("代码生成-代码压缩文件")
    String GEN_ZIP = "/public/gen/zip/{projectCode}";
    @Desc("代码生成-代码生成规则")
    String GEN_RULE = "/public/gen/rule/{projectCode}";



}
