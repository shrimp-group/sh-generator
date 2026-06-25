# TD-003: actuator 端点全暴露并开启 shutdown/restart

## 元信息
| 字段 | 值 |
|------|-----|
| ID | TD-003 |
| 分类 | 安全风险 / 权限缺失 |
| 严重程度 | high |
| 状态 | Open |
| 发现日期 | 2026-06-26 |
| 解决日期 | - |

## 描述
`generator-server-starter/src/main/resources/config/application.yml` 中管理端点配置过于宽松：

```yaml
management:
  endpoints:
    web:
      exposure:
        include: "*"
  server:
    port: 50000
  endpoint:
    health:
      showDetails: always
    shutdown:
      enabled: true
    restart:
      enabled: true
```

问题：
1. `include: "*"` 暴露全部 actuator 端点（含 env、configprops、mappings、beans 等敏感端点）。
2. `shutdown` / `restart` 端点开启，可被未授权调用直接关停应用。
3. `health.showDetails: always` 暴露健康检查细节（数据库连接信息等）。
4. 管理端口 50000 未见独立的鉴权配置。

## 影响范围
- 生产环境一旦以此配置启动，50000 端口可被探测并触发关停或泄露配置。

## 复现条件
1. 以默认 `application.yml` 启动应用。
2. 访问 `http://host:50000/actuator/env` 可见全部环境变量。
3. POST `http://host:50000/actuator/shutdown` 可关停应用。

## 当前解决方案（如有）
无，依赖部署侧手动收窄。

## 建议解决方案
- 通过 `application-prod.yml` 覆盖：`management.endpoints.web.exposure.include: health,info,metrics,prometheus`。
- 生产环境关闭 `shutdown` / `restart` 端点。
- `health.showDetails` 设为 `when_authorized`。
- 管理端口 50000 绑定内网网卡或增加 Spring Security 鉴权。

## 关联模块
- `generator-server-starter/src/main/resources/config/application.yml`

## 关联 Story
- 无直接 Story，属部署配置项
