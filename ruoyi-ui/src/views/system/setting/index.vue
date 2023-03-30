<template>
  <div class="app-container">
    <el-tabs v-model="activeName" tab-position="left">
      <el-tab-pane label="系统配置" name="system">
        <system-setting/>
      </el-tab-pane>
      <el-tab-pane label="主题设置" name="theme">
        <theme-panel style="width: 500px"/>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import ThemePanel from '@/layout/components/Settings/theme'
import SystemSetting from "@/views/system/setting/system-setting";

export default {
  name: 'Setting',
  components: {
    ThemePanel,
    SystemSetting
  },
  data() {
    return {
      activeName: 'system'
    }
  },

}
</script>
