<template>
  <!--  上传附件的弹出窗-->
  <div class="attachment-upload" v-loading="loading">
    <el-drawer
      title="附件上传"
      :visible.sync="visible"
      :with-header="false"
      size="400px"
      @closed="cancel"
    >
      <div style="padding: 20px">
        <el-upload
          ref="uploadFiles"
          class="upload-demo"
          drag
          :action="uploadFileUrl"
          :headers="headers"
          :auto-upload="false"
          style="margin-top: 20px"
          multiple
          :before-upload="handleBeforeUpload"
          :on-progress="handleUpload"
          :on-error="handleUploadError"
          :on-success="handleUploadSuccess"
        >
          <i class="el-icon-upload"></i>
          <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        </el-upload>
      </div>
      <div class="dialog-footer" style="margin-top: 20px;text-align: center">
        <el-button type="primary" @click="handleEnterUpload">确定上传</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-drawer>
  </div>
</template>

<script>
import { getToken } from '../../utils/auth'

export default {
  props: {
    allowFileTypes: {
      type: Array,
      default: () => []
    },
    visible: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      number: 0,
      loading: false,
      fileList: [],
      uploadFileUrl: process.env.VUE_APP_BASE_API + '/common/upload', // 上传的图片服务器地址
      headers: {
        Authorization: 'Bearer ' + getToken()
      }
    }
  },
  methods: {
    cancel() {
      this.$refs.uploadFiles.clearFiles()
      this.visible = false
      this.$emit('update:visible', this.visible)
    },
    handleEnterUpload() {
      this.$refs.uploadFiles.submit()
    },
    /**
     * 开始上传，校验文件格式是否符合
     */
    handleBeforeUpload(file) {
      this.number++
      this.loading = true
    },
    /**
     * 文件开始上传
     */
    handleUpload(event, file, fileList) {
    },
    /**
     * 上传文件失败
     */
    handleUploadError(error, file, fileList) {
      this.loading = false
      this.$emit('on-error', error)
    },
    /**
     * 上传文件成功
     * @param respone
     * @param file
     * @param fileList
     */
    handleUploadSuccess(response, file, fileList) {
      this.number--
      this.fileList.push(response.attachment)
      if (this.number === 0) {
        this.loading = false
        this.$emit('on-success', this.fileList)
      }
    }
  }
}
</script>
