# TD-008: 硬编码 deleted 列过滤

## 元信息
| 字段 | 值 |
|------|-----|
| ID | TD-008 |
| 分类 | 其他潜在风险 / 代码质量 |
| 严重程度 | low |
| 状态 | Open |
| 发现日期 | 2026-06-26 |
| 解决日期 | - |

## 描述
`GenService.getColumns` 在读取列元数据后通过硬编码字符串过滤 `deleted` 列：

```java
return columns.stream().filter(t -> !"deleted".equals(t.getColumnName())).toList();
```

问题：
1. 假定所有目标表都遵循统一的 `deleted` 软删除列命名约定，若业务库使用 `is_deleted`、`del_flag`、`recycled` 等命名则过滤失效。
2. 反之，若业务表恰好有名为 `deleted` 的业务字段（非软删除），会被误过滤。
3. 过滤逻辑硬编码在 Service 层，无法按项目/数据源配置化。

## 影响范围
- 代码生成结果中 `deleted` 列相关字段的生成行为。
- 对命名约定不一致的业务库兼容性差。

## 复现条件
1. 目标库存在一张表，其软删除列名为 `is_deleted`。
2. 触发生成，观察生成代码是否仍包含 `is_deleted` 字段（应为包含，因过滤仅命中 `deleted`）。
3. 目标库存在一张表，含业务字段 `deleted`（非软删除），生成代码中该字段会被误过滤掉。

## 当前解决方案（如有）
硬编码 `"deleted".equals(columnName)` 过滤。

## 建议解决方案
- 将软删除列名配置化：在 `GenProject` 或 `GenDatasource` 增加 `softDeleteColumn` 字段，默认 `deleted`，可按项目覆盖。
- 或在模板层用 FreeMarker 条件判断过滤，而非在元数据采集层硬编码。
- 单元测试覆盖 `deleted`、`is_deleted`、`del_flag` 等多种命名。

## 关联模块
- `generator-server/src/main/java/com/wkclz/generator/server/service/GenService.java`（getColumns 方法，约 213 行）

## 关联 Story
- [代码生成/002-生成代码并下载zip](../../stories/代码生成/002-生成代码并下载zip.md)
