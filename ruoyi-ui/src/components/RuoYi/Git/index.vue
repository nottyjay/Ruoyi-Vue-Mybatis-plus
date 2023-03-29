<template>
  <div>
    <svg-icon icon-class="github" @click="goto"/>
  </div>
</template>

<script>
export default {
  name: 'RuoYiGit',
  data() {
    return {
      url: 'https://gitee.com/nottyjay/ruoyi-vue-mybatis-plus'
    }
  },
  methods: {
    goto() {
      window.open(this.url)
    }
  }
}
</script>
