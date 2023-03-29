<template>
  <el-drawer size="580px" :visible="visible" :with-header="false" :append-to-body="true" :show-close="false">
    <theme-panel/>
  </el-drawer>
</template>

<script>
import ThemePanel from './theme'

export default {
  components: {
    ThemePanel
  },
  computed: {
    visible: {
      get() {
        return this.$store.state.settings.showSettings
      }
    },
  }
}
</script>
