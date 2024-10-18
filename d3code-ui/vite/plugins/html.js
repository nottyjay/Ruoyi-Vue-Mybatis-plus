import {createHtmlPlugin} from 'vite-plugin-html'

export default function createHtml(env) {
  return createHtmlPlugin({
      minify: true,
      inject: {
        data: {
          title: env.VITE_APP_TITLE || 'D3code管理系统'
        }
      }
    }
  )
}
