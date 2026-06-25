# sh-generator 项目上下文

> 本文件为 AI 助手提供项目全局上下文，帮助 AI 快速理解项目全貌。

## 项目概述
代码生成器：基于数据库表结构自动生成后端工程代码（Entity/Mapper/Service/REST 等），提供项目/任务/模板/数据源/日志管理能力

## 技术栈
- 语言：Java
- 运行时：JDK 25
- 框架：Spring Boot
- 构建：Maven

## 核心业务领域
项目管理（GenProject）、任务管理（GenTask）、模板管理（GenTemplate）、数据源管理（GenDatasource）、日志管理（GenLog）、代码生成（GenService）

## 关键约束
多模块 Maven 结构；继承父 POM sh-parent 5.0.0-SNAPSHOT；JDK 25；动态多数据源（sh-dynamicdb）；依赖 iam-sdk 鉴权；前后端分离（generator-ui 独立部署）

## 外部依赖
com.wkclz.iam:iam-sdk（鉴权）、sh-mybatis（持久化）、sh-dynamicdb（动态数据源）、sh-redis（缓存）、sh-web（Web 基础设施）
