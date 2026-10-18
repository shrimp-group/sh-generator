// 公共路由（/login 登录委托门户不在此注册；/redirect、404、/401 由 @sh/ui-core 内置）
// 业务路由全部由服务端菜单按 appCode 下发，此处仅保留 views 组件池
export const routes = [
]

// 动态路由 loadView 的组件池（import.meta.glob 必须在消费方求值）
export const views = import.meta.glob('../views/**/*.vue')
