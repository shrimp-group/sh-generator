// 代码生成器子系统 vite 配置（对齐 sh-k8s-ui：插件装配交由 @sh/ui-core preset，产物相对化支持任意子目录部署）
import path from 'path'
import { defineConfig } from 'vite'
import { createUiCorePreset } from '@sh/ui-core/vite'

// https://vitejs.dev/config/
export default defineConfig(({ command }) => {
  return {
    // dev 固定 '/'（dev 不支持相对 base）；build 相对化：hash 路由下文档路径恒定，产物可部署任意子目录
    base: command === 'build' ? './' : '/',
    plugins: [...createUiCorePreset({ isBuild: command === 'build' })],
    // ui-core dist 中 Logo.vue 的 import "/favicon.svg" 为 public 资产语义（由消费方 public 目录提供），
    // 预构建 esbuild 无法按根路径解析，保持 external 后由 dev 静态服务命中
    optimizeDeps: {
      // monaco 供模板编辑使用，强制预构建（对齐 sh-k8s-ui）
      include: ['monaco-editor'],
      esbuildOptions: { external: ['/favicon.svg'] }
    },
    resolve: {
      // https://cn.vitejs.dev/config/#resolve-alias
      alias: {
        // 设置路径
        '~': path.resolve(__dirname, './'),
        // 设置别名
        '@': path.resolve(__dirname, './src')
      },
      // https://cn.vitejs.dev/config/#resolve-extensions
      extensions: ['.mjs', '.js', '.ts', '.jsx', '.tsx', '.json', '.vue']
    },
    // 打包配置
    build: {
      // https://vite.dev/config/build-options.html
      sourcemap: command === 'build' ? false : 'inline',
      outDir: 'dist',
      assetsDir: 'assets',
      chunkSizeWarningLimit: 2000,
      rollupOptions: {
        output: {
          chunkFileNames: 'static/js/[name]-[hash].js',
          entryFileNames: 'static/js/[name]-[hash].js',
          assetFileNames: 'static/[ext]/[name]-[hash].[ext]'
        }
      }
    },
    // vite 相关配置
    server: {
      port: 5281,
      host: true,
      open: false,
      proxy: {
        // https://cn.vitejs.dev/config/#server-proxy
        // 后端：/api → uat 网关，零 CORS；调试本地生成器后端时将 target 临时改为 http://localhost:8080，或经 localStorage.mock-api 覆盖
        '/api': {
          target: 'https://api.uat.husters.cn',
          changeOrigin: true,
          rewrite: (p) => p.replace(/^\/api/, '')
        },
        // 门户：/portal → 线上门户整站，登录页在本站 origin 运行，token Cookie 落 localhost 同源
        '/portal': {
          target: 'https://portal.uat.husters.cn',
          changeOrigin: true,
          rewrite: (p) => p.replace(/^\/portal/, '')
        }
      }
    },
    css: {
      postcss: {
        plugins: [
          {
            postcssPlugin: 'internal:charset-removal',
            AtRule: {
              charset: (atRule) => {
                if (atRule.name === 'charset') {
                  atRule.remove()
                }
              }
            }
          }
        ]
      }
    }
  }
})
