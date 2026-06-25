# sh-generator

> 代码生成器：基于数据库表结构自动生成后端工程代码，支持项目/任务/模板/数据源/日志管理

## 技术栈
- 语言：Java
- 运行时：JDK 25
- 框架：Spring Boot
- 构建：Maven
- 测试：JUnit 5

## 目录结构
```
sh-generator/
├── generator-server/              # 核心业务模块
│   └── src/main/java/com/wkclz/generator/server/
│       ├── rest/                  # REST 接口（GenProjectRest 等）
│       ├── service/               # 业务服务
│       ├── mapper/                # MyBatis Mapper
│       ├── helper/                # 辅助类
│       └── bean/                  # entity/dto/vo/gen/enums
├── generator-server-starter/      # Spring Boot 启动模块
│   └── src/main/
│       ├── java/.../starter/GeneratorServerApplication.java
│       └── resources/config/application.yml
├── generator-client/              # Maven Mojo 插件模块
│   └── src/main/java/com/wkclz/generator/client/
├── generator-ui/                  # Vue 3 前端
├── docs/                          # 项目文档（规范/活文档/Stories/技术债务）
├── .trae/                         # Trae 配置（skills/mcp.json/.ignore）
├── changes/                       # 变更记录
├── AGENTS.md
├── CONTEXT.md
└── pom.xml                        # 父 POM（继承 sh-parent）
```

## 关键入口
| 入口 | 路径 | 说明 |
|------|------|------|
| 主入口 | generator-server-starter/src/main/java/com/wkclz/generator/server/starter/GeneratorServerApplication.java | 应用启动文件 |
| 配置 | generator-server-starter/src/main/resources/config/application.yml | 配置文件 |
| 路由/接口 | generator-server/src/main/java/com/wkclz/generator/server/rest/ | API 路由定义 |

## 核心模块
- generator-server：核心业务模块（REST 接口、Service、Mapper、Entity/DTO/VO）
- generator-server-starter：Spring Boot 启动模块（主类、配置）
- generator-client：Maven Mojo 插件模块（命令行代码生成）
- generator-ui：Vue 3 前端（Vite + Element Plus + Pinia）

## 代码规范
详见 [docs/coding-standards/java.md](docs/coding-standards/java.md)

## 研发规范
- [研发过程规范](docs/dev-process.md)
- [需求拆解模板](docs/requirement-template.md)
- [技术活文档](docs/living-docs-technical/)
- [业务活文档](docs/living-docs-business/)
- [开发规范](docs/standards/)

## Stories
见 [docs/stories/](docs/stories/) 目录

## 变更记录
见 [changes/](changes/) 目录

## 项目上下文
见 [CONTEXT.md](CONTEXT.md)

## 质量门禁
- lint: `mvn checkstyle:check`
- test: `mvn test`
- build: `mvn package -DskipTests`
- typecheck: `mvn compile`

## 编码规则

> 以下规则为 harness 工程强制规范，AI 编码时必须遵循：

1. **禁止调用系统资源**：仅能使用当前目录下的代码资源，不得调用系统级命令或外部系统资源
2. **保留已有注释**：不要移除已添加的注释，除非相关代码块已变动
3. **关键位置加日志**：实现业务逻辑时，在关键位置添加 log 日志打印（方法入口、分支判断、异常捕获、外部调用）
4. **更新文档**：任务完成后，必须更新本文件（AGENTS.md）以及相关的故事文件
5. **Req/Resp 封装**：所有请求参数封装 Req 对象（除非参数只有一个值），所有返回内容封装 Resp 对象（除非返回只有一个值）
