# sh-generator 技术活文档

> 最后更新：`2026-06-26`

## 技术栈说明

sh-generator 是一个基于 Maven 多模块结构的代码生成器，技术栈如下：

| 维度 | 技术选型 |
|------|----------|
| 语言 | Java（JDK 25） |
| 应用框架 | Spring Boot（由父 POM `com.wkclz.framework:sh-parent:5.0.0-SNAPSHOT` 统一管理） |
| 构建 | Maven 多模块（`<revision>5.0.0-SNAPSHOT</revision>`） |
| 持久层 | MyBatis + PageHelper（mapper 定位于 `classpath*:mapper/**/*.xml`） |
| 动态数据源 | sh-dynamicdb（`DynamicDataSourceHolder` 切换目标库读取表/列元数据） |
| 模板引擎 | FreeMarker（`FreeMarkerTemplateUtil.parseString` 渲染模板字符串） |
| Web | Spring MVC + iam-sdk 鉴权（`@CurrentMember` 注入当前用户） |
| 缓存 | sh-redis |
| 前端 | generator-ui（独立子项目） |
| Maven 插件 | generator-client（Maven Mojo，封装生成能力供外部工程调用） |

## 文档索引

本项目技术文档按照以下结构组织：

| 目录 | 说明 |
|------|------|
| [architecture/](architecture/) | 系统架构与部署相关文档 |
| [modules/](modules/) | 模块索引与详细说明 |
| [api/](api/) | API 接口文档 |
| [data-models/](data-models/) | 数据模型文档 |
| [dependencies/](dependencies/) | 依赖关系文档 |

## 子目录链接

- [架构概览](architecture/overview.md)
- [部署架构](architecture/deployment.md)
- [模块索引](modules/README.md)
- [API索引](api/README.md)
- [数据模型索引](data-models/README.md)
- [依赖索引](dependencies/README.md)
