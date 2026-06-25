# 架构概览

## 系统架构图

```mermaid
flowchart TB
    subgraph Client[调用方]
        UI[generator-ui<br/>Vue3 + Element Plus]
        Mojo[generator-client<br/>Maven Mojo]
    end

    subgraph Server[generator-server-starter :8080]
        Rest[Gen*Rest<br/>Spring MVC Controller]
        Svc[Gen*Service<br/>业务服务层]
        GenSvc[GenService<br/>核心生成引擎]
        Helper[GenParamHFetchelper<br/>参数装配]
        Free[FreeMarkerTemplateUtil<br/>模板渲染]
    end

    subgraph Meta[元数据层]
        Dynamic[DynamicDataSourceHolder<br/>sh-dynamicdb]
        TableMapper[TableInfoMapper<br/>读 information_schema]
    end

    subgraph DB[(MySQL)]
        GenTables[(gen_project<br/>gen_task<br/>gen_template<br/>gen_datasource<br/>gen_log)]
        TargetDB[(用户业务库<br/>按 dbCode 动态切换)]
    end

    UI -->|HTTP| Rest
    Mojo -->|HTTP / 直接调用| Rest
    Rest --> Svc
    Svc --> GenSvc
    GenSvc --> Dynamic
    Dynamic -->|切换 dbCode| TargetDB
    GenSvc --> TableMapper
    TableMapper -->|读取表/列| TargetDB
    GenSvc --> Helper
    GenSvc --> Free
    Svc --> GenTables
```

## 架构描述

sh-generator 采用经典的"配置 → 元数据采集 → 模板渲染 → 产物输出"四段式代码生成架构：

1. **配置层**：用户通过 `gen_project`（生成项目）、`gen_task`（生成任务）、`gen_template`（模板）、`gen_datasource`（目标数据源）四张主表完成生成配置。一个生成项目绑定一个目标数据源（`dbCode`）和一组表前缀（`tablePrefix`）。
2. **元数据采集层**：`GenService.getGenData` 通过 `DynamicDataSourceHolder.set(project.getDbCode())` 将当前线程切换到用户配置的目标库，再由 `TableInfoMapper` 读取 `information_schema` 获取表与列的元数据，组装成 `GenParam`。
3. **模板渲染层**：`GenService.genCodeData` 加载 `gen_template` 中以 FreeMarker 语法编写的模板字符串，调用 `FreeMarkerTemplateUtil.parseString(tempContent, map)` 渲染为最终代码文本。
4. **产物输出层**：渲染结果按 `projectBasePath + packagePath` 写入临时目录（`{catalina.base}/gen/{yyyyMMddHHmmssSSS}`），随后 `CompressUtil.zip` 打包并通过 `HttpServletResponse` 以 `application/octet-stream` 推送给调用方。

整体为单体应用（`generator-server-starter` 独立端口 8080），无微服务拆分；`generator-client` 以 Maven Mojo 形式复用 server 的 HTTP 接口对外提供命令行生成能力。

## 核心技术组件说明

| 组件 | 说明 | 技术选型 |
|------|------|----------|
| GenService | 代码生成核心引擎，串联元数据采集、模板渲染、产物压缩与下载 | Spring `@Service` + FreeMarker |
| DynamicDataSourceHolder | 按目标数据源编码动态切换当前线程的 JDBC 连接 | sh-dynamicdb（基于 AbstractRoutingDataSource） |
| TableInfoMapper | 读取目标库 `information_schema` 的表与列元数据 | MyBatis（mapper xml 位于 `classpath*:mapper/`） |
| GenParamHFetchelper | 将表/列元数据与生成任务装配为渲染参数 `GenParam` | 纯 Java 工具类 |
| FreeMarkerTemplateUtil | 解析模板字符串并渲染（模板内容存储在 `gen_template.temp_content`） | FreeMarker |
| Gen*Rest | 项目/任务/模板/数据源/日志/客户生成的 REST 入口 | Spring MVC + iam-sdk `@CurrentMember` |
| CompressUtil | 将生成目录打包为 zip | sh-tool |

## 组件交互流程

典型生成一次代码的交互时序：

1. 调用方（UI 或 Mojo）发起 `GET /gen/customer/{projectCode}/zip`。
2. `GenCustomerRest.genZip` 进入 `GenService.getGenZip`：
   - 先向 `gen_log` 插入一条日志（记录 `startTime`、`genPath`）。
   - 调用 `genCodeData`：通过 `DynamicDataSourceHolder.set` 切库 → `TableInfoMapper.getTables/getColumns` 取元数据 → `GenParamHFetchelper.fetchGenParam` 装配 → 遍历 `GenPkg` 逐表渲染写入磁盘。
   - 渲染完成后回写 `gen_log.endTime`。
3. `zipDataAndPush` 将磁盘目录打包为 zip，写入 `HttpServletResponse` 输出流。
4. 调用方接收 zip 文件。
