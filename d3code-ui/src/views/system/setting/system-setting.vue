<template>
  <div class="system-setting">
    <el-form :model="form" label-width="180px">
      <h3 class="drawer-title">系统管理员配置</h3>
      <el-divider/>
      <el-row :gutter="30">
        <el-col :span="6">
          <el-form-item label="管理员初始密码">
            <el-input v-model="form['sys.user.initPassword']" placeholder="请输入初始密码"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="登陆验证码">
            <el-switch v-model="form['sys.account.captchaEnabled']" class="drawer-switch"/>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="自助注册">
            <el-switch v-model="form['sys.account.registerUser']" class="drawer-switch"/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-divider/>

      <el-button size="small" type="primary" plain icon="el-icon-document-add" @click="saveSetting">保存配置
      </el-button>
    </el-form>
  </div>
</template>

<script>
import {getConfigKey, updateConfigByKey} from '@/api/system/config'

export default {
  data() {
    return {
      keys: ['sys.user.initPassword', 'sys.account.captchaEnabled', 'sys.account.registerUser'],
      form: {
        'sys.user.initPassword': '',
        'sys.account.captchaEnabled': false,
        'sys.account.registerUser': false,
        'sys.storage.type': 'local'
      }
    }
  },
  created() {
    this.$nextTick(() => {
      this.init()
    })
  },
  methods: {
    init() {
      getConfigKey('sys.user.initPassword').then(response => {
        this.form['sys.user.initPassword'] = response.msg
      })
      getConfigKey('sys.account.captchaEnabled').then(response => {
        this.form['sys.account.captchaEnabled'] = Boolean(response.msg)
      })
      getConfigKey('sys.account.registerUser').then(response => {
        this.form['sys.account.registerUser'] = Boolean(response.msg)
      })
    },
    saveSetting() {
      Object.keys(this.form).map(key => {
        const value = this.form[key]
        updateConfigByKey({configKey: key, configValue: value})
      })
      this.$modal.msgSuccess('配置保存成功');
    },
  }
}
</script>
