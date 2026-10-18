// 代码生成器子系统装配：共享层全部由 @sh/ui-core 承担（对齐 sh-k8s-ui）
// ElementPlus 全局 props 定制与全局属性/组件挂载均由 @sh/ui-core dist/core/installCommon 完成，此处无需重复
import { createAdminApp } from '@sh/ui-core'
import { runtimeEnv } from '../env.js'
import { routes, views } from './router'

createAdminApp({
  env: runtimeEnv,
  routes,
  views,
  // 管理端无登录入口（登录委托门户），白名单为空
  whiteList: [],
  // 菜单由服务端按 appCode 下发
  menuSource: 'remote',
  // 外观偏好（沿用原单体 settings.js 默认值；用户已在「布局设置」保存的偏好优先）
  feature: {
    sideTheme: 'theme-light',
    navType: 1,
    tagsView: true,
    tagsIcon: true,
    fixedHeader: true,
    sidebarLogo: true,
    dynamicTitle: true,
    showSettings: true,
    footerVisible: false,
    footerContent: 'Copyright © 2018-2025 wangkc. All Rights Reserved.'
  }
})
