<template>
  <div class="parser-container">
    <ng-form-build :formTemplate="config.formTemplate" :models="config.models" ref="formBuild" :disabled="disabled"/>
    <el-form v-if="needSubmit" :label-width="config.formTemplate.config.labelWidth + 'px'"
             :label-position="config.formTemplate.config.labelPosition"
    >
      <el-form-item>
        <el-button type="primary" @click="submitForm">提交</el-button>
        <el-button @click="restForm">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>

export default {
  props: {
    config: Object,
    disabled: false,
    needSubmit: true
  },
  methods: {
    submitForm() {
      console.log(this.$refs['formBuild'])
      this.$refs['formBuild'].getData(false).then(data => {
        this.$emit('submit', data)
      })

    },
    restForm() {
      this.$refs['formBuild'].reset()
    }
  }
}

</script>
