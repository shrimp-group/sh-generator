# TD-007: IOException 被吞没

## 元信息
| 字段 | 值 |
|------|-----|
| ID | TD-007 |
| 分类 | 其他潜在风险 / 可观测性 |
| 严重程度 | medium |
| 状态 | Open |
| 发现日期 | 2026-06-26 |
| 解决日期 | - |

## 描述
`GenService.genCodeData` 的 finally 块中，关闭 `FileOutputStream` 时捕获的 `IOException` 被空 catch 吞没：

```java
catch (IOException e) {
    // who care ?
}
```

同样，`zipDataAndPush` 中压缩与下载的异常被 `log.error("Exception", var34)` 记录但不再向上抛出，调用方可能拿到不完整的 zip 响应却无法感知。

问题：
1. 关闭流时的真实 IO 异常（如磁盘满）被完全屏蔽，排障时无任何线索。
2. 压缩/下载失败时调用方无感知，用户体验为"下载了一个损坏的 zip"。
3. 注释 `who care ?` 反映了对异常处理的轻视，违背"关键位置加日志"的强制编码规则。

## 影响范围
- 生产排障困难。
- 用户可能拿到不完整的生成产物。

## 复现条件
1. 模拟磁盘满或文件权限错误导致 `fos.close()` 抛 IOException。
2. 观察日志，无任何记录。

## 当前解决方案（如有）
空 catch 吞没。

## 建议解决方案
- 至少 `log.warn("关闭文件流失败: {}", e.getMessage(), e)` 记录异常堆栈。
- `zipDataAndPush` 中压缩失败应抛 `SystemException`，让调用方明确感知失败。
- 移除 `who care ?` 注释，符合团队"关键位置加日志"规则。

## 关联模块
- `generator-server/src/main/java/com/wkclz/generator/server/service/GenService.java`（genCodeData 约 152-154 行，zipDataAndPush 约 186-188 行）

## 关联 Story
- [代码生成/002-生成代码并下载zip](../../stories/代码生成/002-生成代码并下载zip.md)
