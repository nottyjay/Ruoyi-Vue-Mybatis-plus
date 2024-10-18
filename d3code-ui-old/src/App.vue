<template>
  <div id="app">
    <router-view />
    <theme-picker />
  </div>
</template>

<script>
import ThemePicker from "@/components/ThemePicker";
import {getConfigKey} from '@/api/system/config'

export default {
  name: "App",
  components: { ThemePicker },
    metaInfo() {
        return {
            title: this.$store.state.settings.dynamicTitle && this.$store.state.settings.title,
            titleTemplate: title => {
                return title ? `${title} - ${process.env.VUE_APP_TITLE}` : process.env.VUE_APP_TITLE
            }
        }
    },
  mounted() {
    getConfigKey('sys.theme.setting').then(response => {
      this.$cache.local.set(
        "layout-setting",
        response.msg
      );
      let setting = JSON.parse(response.msg)
      setting['errorLog'] = 'production'
      this.$store.dispatch('settings/resetSetting', setting)
    })
  }
};
</script>
<style scoped>
#app .theme-picker {
  display: none;
}
</style>
