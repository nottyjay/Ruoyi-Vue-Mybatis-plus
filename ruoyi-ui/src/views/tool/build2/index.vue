<template>
  <div class="app-container">
    <ng-form-design ref="formDesign" :template.sync="formTemplate"/>
  </div>
</template>

<script>

export default {
  name: 'Build2',
  data() {
    return {
      formTemplate: null,
    }
  },
  created() {
    // 防止 firefox 下 拖拽 会新打卡一个选项卡
    document.body.ondrop = event => {
      event.preventDefault()
      event.stopPropagation()
    }
  }
}
</script>
