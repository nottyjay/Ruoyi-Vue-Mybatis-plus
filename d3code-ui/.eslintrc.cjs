/* eslint-env node */
require('@rushstack/eslint-patch/modern-module-resolution')

module.exports = {
  root: true,
  extends: [
    'plugin:vue/vue3-essential', 
    'eslint:recommended', 
    '@vue/eslint-config-prettier',
    './.eslintrc-auto-import.json'
  ],
  env: {
    'vue/setup-compiler-macros': true
  },
  rules: {
    'prettier/prettier': [
      'warn',
      {
        singleQuote: true, // 单引号
        semi: false, // 无分号
        printWidth: 100, // 每行宽度至多80字符
        trailingComma: 'none', // 不加对象|数组最后逗号
        endOfLine: 'auto' // 换行符号不限制（win mac 不一致）
      }
    ],
    // 关闭驼峰命名规则
    'vue/multi-word-component-names': 0,
    'no-unused-vars': 2
  }
}
