<template>
  <div class="app-container">
    <ng-form-design ref="formDesign" :template.sync="formTemplate" :imp="false" :exp="false">
      <template v-slot:controlButton>
        <el-button type="text" size="medium" @click="handleSaveDialog">保存</el-button>
      </template>
    </ng-form-design>

    <el-dialog title="保存当前表单" :visible.sync="open" width="680px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="表单名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入表单名称"/>
        </el-form-item>
        <el-form-item label="表单状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in dict.type.sys_normal_disable"
              :key="dict.value"
              :label="dict.value"
            >{{ dict.label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="表单备注" prop="remark">
          <el-input type="textarea" v-model="form.remark" placeholder="请输入备注信息"/>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>

import { addBpmForm, getBpmForm, updateBpmForm } from '@/api/bpm/bpmForm'
import { decodeFields } from '@/utils/formGenerator'

export default {
  name: 'BpmFormDesign',
  dicts: ['sys_normal_disable'],
  data() {
    return {
      // 表单信息
      form: {},
      // 表单初始模板
      formTemplate: null,
      // 保存窗口弹窗
      open: false,
      rules: {
        name: [
          { required: true, message: '表单名称不能为空', trigger: 'blur' }
        ],
        status: [
          { required: true, message: '表单状态不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    // 防止 firefox 下 拖拽 会新打卡一个选项卡
    document.body.ondrop = event => {
      event.preventDefault()
      event.stopPropagation()
    }

    this.$nextTick(() => {
      this.form.id = this.$route.params.id
      if (this.form.id != null) {
        this.loadForm()
      }
    })
  },
  methods: {
    loadForm() {
      getBpmForm(this.form.id).then(response => {
        this.form = response.data
        this.form.conf = JSON.parse(this.form.conf)
        this.formTemplate = { config: this.form.conf, list: decodeFields(this.form.fields) }
      })
    },
    handleSaveDialog() {
      this.open = true
    },
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          const formDesign = this.$refs['formDesign'].getModel()
          this.form.conf = JSON.stringify(formDesign.config)
          this.form.fields = formDesign.list.map(item => {
            return JSON.stringify(item)
          })
          if (this.form.id == undefined) {
            // 保存表单
            addBpmForm(this.form).then(response => {
              this.$modal.msgSuccess('表单保存成功')
              this.open = false
            })
          } else {
            // 更新表单
            updateBpmForm(this.form).then(response => {
              this.$modal.msgSuccess('表单更新成功')
              this.open = false
            })
          }
        }
      })
    }
  }
}
</script>
