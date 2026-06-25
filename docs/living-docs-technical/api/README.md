# API 索引

> 所有接口由 `generator-server-starter` 在 8080 端口提供，鉴权依赖 iam-sdk，统一以 `@CurrentMember` 注入当前用户。返回值由 `sh-web` 框架统一封装为标准 `Result` 结构。

## API 列表

| 方法 | 路径 | 说明 | 模块 |
|------|------|------|------|
| GET | `/gen/project/page` | 生成项目分页查询 | GenProjectRest |
| GET | `/gen/project/detail` | 生成项目详情（按 id） | GenProjectRest |
| POST | `/gen/project/create` | 新建生成项目 | GenProjectRest |
| POST | `/gen/project/update` | 更新生成项目 | GenProjectRest |
| POST | `/gen/project/remove` | 删除生成项目 | GenProjectRest |
| POST | `/gen/project/copy` | 复制生成项目 | GenProjectRest |
| GET | `/gen/task/list` | 生成任务列表（按 projectCode） | GenTaskRest |
| POST | `/gen/task/save` | 批量保存生成任务 | GenTaskRest |
| POST | `/gen/task/remove` | 删除生成任务 | GenTaskRest |
| GET | `/gen/template/page` | 模板分页查询 | GenTemplateRest |
| GET | `/gen/template/detail` | 模板详情 | GenTemplateRest |
| POST | `/gen/template/create` | 新建模板 | GenTemplateRest |
| POST | `/gen/template/update` | 更新模板 | GenTemplateRest |
| POST | `/gen/template/remove` | 删除模板 | GenTemplateRest |
| GET | `/gen/template/options` | 模板下拉选项 | GenTemplateRest |
| GET | `/gen/datasource/page` | 数据源分页查询 | GenDatasourceRest |
| GET | `/gen/datasource/detail` | 数据源详情（脱敏，dbPassword 置 null） | GenDatasourceRest |
| POST | `/gen/datasource/create` | 新建数据源 | GenDatasourceRest |
| POST | `/gen/datasource/update` | 更新数据源 | GenDatasourceRest |
| POST | `/gen/datasource/remove` | 删除数据源 | GenDatasourceRest |
| GET | `/gen/datasource/options` | 数据源下拉选项 | GenDatasourceRest |
| GET | `/gen/log/page` | 生成日志分页查询 | GenLogRest |
| GET | `/gen/log/detail` | 生成日志详情 | GenLogRest |
| GET | `/gen/customer/{projectCode}/data` | 预览生成数据（GenParam 列表） | GenCustomerRest |
| GET | `/gen/customer/{projectCode}/zip` | 生成代码并以 zip 下载 | GenCustomerRest |
| GET | `/gen/customer/{projectCode}/rule` | 预览生成规则（GenTaskDto 列表） | GenCustomerRest |

## API 详细文档

### 项目管理（GenProjectRest）

提供生成项目的 CRUD 与复制能力。生成项目是代码生成的最顶层配置，绑定目标数据源（`dbCode`）、表前缀（`tablePrefix`）、模块名（`moduleName`）等。

- 入参校验：使用 `Assert.notNull` 校验关键字段。
- `copy` 接口会基于已有项目复制一份配置，便于快速搭建相似生成任务。

### 任务管理（GenTaskRest）

管理一个项目下的生成任务集合。一个任务绑定一个模板（`tempCode`）与目标包路径（`packagePath`、`projectBasePath`），并控制是否生成（`createSwitch`）与是否删除（`deleteSwitch`）。

- `save` 为批量保存，校验规则：一批必须属于同一 `projectCode`，同一批任务中 `tempCode` 不允许重复。

### 模板管理（GenTemplateRest）

管理 FreeMarker 模板。模板内容（`tempContent`）以字符串形式存储在 `gen_template.temp_content`，渲染时由 `FreeMarkerTemplateUtil.parseString` 解析。`tempKey` 标识模板用途，`tempSubfix` 决定生成文件后缀。

### 数据源管理（GenDatasourceRest）

管理目标业务库连接信息。`detail` 接口返回前将 `dbPassword` 置为 `null`，避免密码回显到前端。生成代码时通过 `DynamicDataSourceHolder.set(dbCode)` 切换到该数据源读取表/列元数据。

### 日志管理（GenLogRest）

只读查询生成日志（`gen_log`），记录每次生成的 `startTime` / `endTime` / `genPath` / `projectCode`。

### 代码生成（GenCustomerRest）

- `data`：返回某项目的生成参数 `GenParam` 列表，便于预览将要生成的数据。
- `rule`：返回某项目的生成任务 `GenTaskDto` 列表。
- `zip`：触发实际生成，落盘渲染结果并打包 zip 通过 `HttpServletResponse` 返回；同时写一条 `gen_log` 记录。

## API 变更日志

| 版本 | 变更类型 | 变更内容 | 日期 |
|------|----------|----------|------|
| 5.0.0-SNAPSHOT | 初始 | 建立项目/任务/模板/数据源/日志/客户生成 6 组 REST 接口 | 2026-06-26 |

## 认证与授权

接口鉴权由 iam-sdk 提供，通过 `@CurrentMember` 注入当前登录用户上下文。`userCode` 字段贯穿 `gen_project`、`gen_task`、`gen_template`、`gen_datasource`、`gen_log` 五张表，用于数据隔离。具体鉴权策略与 token 校验流程由 iam 服务统一管理，本仓库不展开。
