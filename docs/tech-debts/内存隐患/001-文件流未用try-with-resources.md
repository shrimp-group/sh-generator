# TD-005: 文件流未用 try-with-resources

## 元信息
| 字段 | 值 |
|------|-----|
| ID | TD-005 |
| 分类 | 内存隐患 / 资源泄漏 |
| 严重程度 | medium |
| 状态 | Open |
| 发现日期 | 2026-06-26 |
| 解决日期 | - |

## 描述
`GenService.genCodeData` 与 `zipDataAndPush` 中文件流的关闭采用手动 `finally` 块而非 try-with-resources：

```java
FileOutputStream fos = null;
PrintStream ps = null;
try {
    ...
} finally {
    if (ps != null) { ps.flush(); ps.close(); }
    if (fos != null) {
        try { fos.flush(); fos.close(); }
        catch (IOException e) { /* who care ? */ }
    }
}
```

`zipDataAndPush` 中 `FileOutputStream fos = new FileOutputStream(distPath + ".zip")` 甚至完全未在 finally 中关闭（仅 `CompressUtil.zip` 内部可能关闭）。

问题：
1. 异常路径下若 `ps.close()` 抛异常，`fos.close()` 不会执行，文件句柄泄漏。
2. `zipDataAndPush` 的 fos 关闭依赖 `CompressUtil` 内部实现，存在不确定性。
3. JDK 25 已全面支持 try-with-resources，无需手动管理。

## 影响范围
- 高频生成场景下文件句柄累积，可能触发 `Too many open files`。

## 复现条件
1. 模拟 `PrintStream.close` 抛异常。
2. 观察文件句柄是否泄漏。

## 当前解决方案（如有）
手动 finally 关闭，吞没 IOException。

## 建议解决方案
- 全部文件流改用 try-with-resources。
- `zipDataAndPush` 的 `FileOutputStream` 显式纳入 try-with-resources。
- 移除空 catch，至少 `log.warn` 记录。

## 关联模块
- `generator-server/src/main/java/com/wkclz/generator/server/service/GenService.java`（genCodeData 约 124-156 行，zipDataAndPush 约 165-169 行）

## 关联 Story
- [代码生成/002-生成代码并下载zip](../../stories/代码生成/002-生成代码并下载zip.md)
