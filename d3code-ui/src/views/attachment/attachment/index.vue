<template>
  <div class="app-container">
    <!-- 附件面板 -->
    <attachment-panel @askFileInformation="fileInformation" :isEdit="true">
      <el-button
        type="primary"
        plain
        icon="Plus"
        @click="handleAdd"
        v-hasPermi="['attachment:attachment:add']"
        >新增
      </el-button>
    </attachment-panel>
    <!-- 添加或修改文件管理对话框-->
    <attachment-upload v-model:visible="open" @on-success="handleUploadSuccess" />

    <!-- 附件详情信息 -->
    <el-drawer title="我是标题" v-model:visible="drawer" :with-header="false" size="600px">
      <div style="font-size: 18px; padding: 20px 20px 0; font-weight: bold">文件详情</div>
      <el-divider></el-divider>
      <div style="padding: 20px">
        <el-form ref="form" :model="form" label-width="110px" label-position="left">
          <el-form-item label="文件名称">
            <span>{{ form.name }}</span>
          </el-form-item>
          <el-form-item label="创建时间">
            <span>{{ form.createTime }}</span>
          </el-form-item>
          <el-form-item label="存储方式">
            <dict-tag :options="dict.type.storage_type" :value="form.storageType" />
          </el-form-item>
          <el-form-item label="存储路径">
            <span>{{ form.path }}</span>
          </el-form-item>
          <el-form-item label="外网访问路径">
            <div style="display: flex">
              <span>{{ form.url }}</span>
              <div>
                <el-link
                  :underline="false"
                  v-clipboard:copy="form.url"
                  v-clipboard:success="clipboardSuccess"
                >
                  <i class="el-icon-document-copy" style="font-size: 20px" />
                </el-link>
              </div>
            </div>
          </el-form-item>
        </el-form>
      </div>
    </el-drawer>
  </div>
</template>

<script>
import { getToken } from '@/utils/auth'
import AttachmentUpload from '@/components/AttachmentUpload/index.vue'
import AttachmentPanel from '@/components/AttachmentPanel/index.vue'

export default {
  name: 'Attachment',
  components: {
    AttachmentUpload,
    AttachmentPanel
  },
  dicts: ['storage_type'],
  data() {
    return {
      ossConfig: [],
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 文件管理表格数据
      attachmentList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 20,
        createBy: null,
        name: null,
        storageType: null
      },
      // 表单参数
      form: {
        id: null,
        createTime: null,
        createBy: null,
        updateTime: null,
        updateBy: null,
        deleted: null,
        name: null,
        storageType: null,
        path: null,
        remark: null
      },
      // 表单校验
      rules: {},
      showPreview: false,
      previewImg: null,
      uploadFileUrl: ref(import.meta.env.VITE_APP_BASE_API + '/common/upload'), // 上传的图片服务器地址
      headers: {
        Authorization: 'Bearer ' + getToken()
      },
      drawer: false
    }
  },
  created() {
    // this.getList()
  },
  methods: {
    /** 查询文件管理列表 */
    // getList() {
    //   this.loading = true
    //   getEnabledEngineConfig().then(response => {
    //     this.ossConfig = response.data
    //     return listAttachment(this.queryParams)
    //   }).then(response => {
    //     this.attachmentList = response.rows
    //     this.total = response.total
    //     this.loading = false
    //   })
    // },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        createTime: null,
        createBy: null,
        updateTime: null,
        updateBy: null,
        deleted: null,
        name: null,
        storageType: null,
        path: null,
        remark: null
      }
      this.resetForm('form')
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '添加文件管理'
    },
    // 上传成功的回调
    handleUploadSuccess(fileList) {
      this.$modal.msgSuccess('添加成功')
      this.open = false
      this.getList()
    },
    // 删除文件
    handleDeleteFile(index) {
      this.$modal
        .confirm('是否确认删除此文件？')
        .then(function () {
          return ''
        })
        .then(() => {
          this.attachmentList.splice(index, 1)
          this.getList()
          this.$modal.msgSuccess('删除成功')
        })
        .catch(() => {})
    },
    // 点击附件详情
    fileInformation(item) {
      this.drawer = true
      this.form = item
    },
    clipboardSuccess() {
      this.$modal.msgSuccess('复制成功')
    }
  }
}
</script>
