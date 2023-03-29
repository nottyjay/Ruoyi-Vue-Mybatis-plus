<template>
  <div class="app-container">
    <el-tabs v-model="activeName" tab-position="left">
      <el-tab-pane label="系统配置" name="first">用户管理</el-tab-pane>
      <el-tab-pane label="主题设置" name="second">
        <theme-panel style="width: 500px"/>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import ThemePanel from '@/layout/components/Settings/theme'

export default {
  components: {
    ThemePanel
  },
  data() {
    return {
      activeName: 'first'
    }
  },

}
</script>
