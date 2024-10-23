<template>
  <div class="app-container">
    <!-- 附件面板 -->
    <attachment-panel @askFileInformation="fileInformation" :isEdit="true">

    </attachment-panel>
    <!-- 添加或修改文件管理对话框-->
    <attachment-upload v-model:visible="open" @on-success="handleUploadSuccess" />

    <!-- 附件详情信息 -->
    <el-drawer v-model="drawer" destroy-on-close :with-header="false" size="600px">
      <div style="font-size: 18px; padding: 20px 20px 0; font-weight: bold">文件详情</div>
      <el-divider></el-divider>
      <div style="padding: 20px">
        <el-form ref="formRef" :model="form" label-width="110px" label-position="left">
          <el-form-item label="文件名称">
            <span>{{ form.name }}</span>
          </el-form-item>
          <el-form-item label="创建时间">
            <span>{{ form.createTime }}</span>
          </el-form-item>
          <el-form-item label="存储方式">
            <dict-tag :options="storage_type" :value="form.storageType" />
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
                    icon="DocumentCopy"
                    v-copyText="form.url"
                    v-copyText:callback="copyUrlSuccess"
                    style="float: right;font-size: 20px;margin-top: 7px"
                ></el-link>
              </div>
            </div>
          </el-form-item>
        </el-form>
      </div>
    </el-drawer>
  </div>
</template>

<script setup name="Attachment">
import { getToken } from '@/utils/auth'
import AttachmentUpload from '@/components/AttachmentUpload/index.vue'
import AttachmentPanel from '@/components/AttachmentPanel/index.vue'


const { proxy } = getCurrentInstance()
const { storage_type } = proxy.useDict('storage_type')

const ossConfig = ref([])
const attachmentList = ref([])
const loading = ref(true)
const showSearch = ref(true)
const total = ref(0)
const drawer = ref(false)
const title = ref('')
const open = ref(false)
const uploadFileUrl = ref(import.meta.env.VITE_APP_BASE_API + '/common/upload')
const previewImg = ref(null)
const headers = ref({
  Authorization: 'Bearer ' + getToken()
})

const data = reactive({
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
  queryParams: {
    pageNum: 1,
    pageSize: 20,
    createBy: null,
    name: null,
    storageType: null
  },
  groupQueryParams: {
    pageNum: 1,
    pageSize: 10,
    groupId: 0
  },
  rules: {},
})

const { form, queryParams, groupQueryParams, rules } = toRefs(data)

// 表单重置
function reset() {
  form.value = {
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
  proxy.resetForm('formRef')
}

function test(url){
  console.log(url,'df')
  return url
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.pageNum = 1
  getList()
}
/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm('queryForm')
  handleQuery()
}
/** 新增按钮操作 */
function handleAdd() {
  reset()
  open.value = true
  title.value = '添加文件管理'
}
// 上传成功的回调
function handleUploadSuccess(fileList) {
  proxy.$modal.msgSuccess('添加成功')
  open.value = false
  getList()
}

// 删除文件
function handleDeleteFile(index) {
  proxy.$modal
    .confirm('是否确认删除此文件？')
    .then(function () {
      return ''
    })
    .then(() => {
      attachmentList.value.splice(index, 1)
      getList()
      proxy.$modal.msgSuccess('删除成功')
    })
    .catch(() => {})
}
// 点击附件详情
function fileInformation(item) {
  drawer.value = true
  form.value = item
}

function copyUrlSuccess() {
  proxy.$modal.msgSuccess('复制成功')
}


</script>
