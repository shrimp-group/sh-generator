# 业务模块索引

最后更新：`2026-06-26`

## 模块列表

| 模块名 | 业务职责 | 关联技术模块 |
|--------|----------|--------------|
| 项目管理 | 维护代码生成的最顶层配置：项目编码、目标数据源、表前缀、模块名 | GenProjectRest / GenProjectService / gen_project |
| 任务管理 | 维护一个项目下的生成任务集合，绑定模板与目标包路径，控制是否生成/删除 | GenTaskRest / GenTaskService / gen_task |
| 模板管理 | 维护 FreeMarker 模板内容与文件后缀，是生成渲染的输入 | GenTemplateRest / GenTemplateService / gen_template |
| 数据源管理 | 维护被读取元数据的目标业务库连接信息，支持密码脱敏 | GenDatasourceRest / GenDatasourceService / gen_datasource |
| 日志管理 | 只读查询每次代码生成的开始/结束时间与产物路径 | GenLogRest / GenLogService / gen_log |
| 代码生成 | 核心生成引擎：采集元数据 → 装配参数 → 渲染模板 → 打包下载 | GenCustomerRest / GenService |

## 模块详细说明

### 项目管理

- **概述**：生成项目是代码生成的最顶层配置实体。一个项目绑定一个目标数据源（`dbCode`）、一组表前缀（`tablePrefix`）、模块名（`moduleName`）。
- **功能列表**：项目分页查询、详情、创建、更新、删除、复制。
- **领域模型**：`GenProject`（详见数据模型文档）。
- **API 设计**：`/gen/project/{page|detail|create|update|remove|copy}`。
- **事件定义**：项目复制会基于已有项目克隆一份配置，便于快速搭建相似生成任务。

### 任务管理

- **概述**：一个项目下可配置多条生成任务，每条任务描述"用哪个模板、生成到哪个包路径、是否生成、是否删除"。
- **功能列表**：任务列表查询（按 projectCode）、批量保存、删除。
- **领域模型**：`GenTask`。
- **API 设计**：`/gen/task/{list|save|remove}`。
- **业务约束**：批量保存时一批必须属于同一 `projectCode`；同一批任务中 `tempCode` 不允许重复。

### 模板管理

- **概述**：模板以 FreeMarker 语法编写，内容存储于 `gen_template.temp_content`。`tempSubfix` 决定生成文件后缀（如 `.java`、`.xml`）。
- **功能列表**：模板分页查询、详情、创建、更新、删除、下拉选项。
- **领域模型**：`GenTemplate`。
- **API 设计**：`/gen/template/{page|detail|create|update|remove|options}`。

### 数据源管理

- **概述**：数据源描述被读取元数据的目标业务库连接信息。生成代码时通过 `DynamicDataSourceHolder.set(dbCode)` 切换到该数据源。
- **功能列表**：数据源分页查询、详情（密码脱敏）、创建、更新、删除、下拉选项。
- **领域模型**：`GenDatasource`。
- **API 设计**：`/gen/datasource/{page|detail|create|update|remove|options}`。
- **安全约束**：`detail` 接口返回前将 `dbPassword` 置为 `null`，避免密码回显。

### 日志管理

- **概述**：记录每次代码生成的开始/结束时间与产物落盘路径，便于追溯与排障。
- **功能列表**：日志分页查询、详情（只读）。
- **领域模型**：`GenLog`。
- **API 设计**：`/gen/log/{page|detail}`。

### 代码生成

- **概述**：核心生成引擎。串联元数据采集、参数装配、模板渲染、产物打包下载。
- **功能列表**：预览生成数据（`GenParam` 列表）、预览生成规则（`GenTaskDto` 列表）、生成代码并下载 zip。
- **API 设计**：`/gen/customer/{projectCode}/{data|rule|zip}`。
- **关键流程**：详见 [业务流程索引](../processes/README.md)。

---

*本文件基于 sh-generator 源码分析生成，后续随业务演进持续更新*
