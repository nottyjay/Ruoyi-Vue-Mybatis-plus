<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="配置名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入配置名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery"
          >搜索</el-button
        >
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Plus"
          @click="handleAdd"
          v-hasPermi="['oss:oss_config:add']"
          >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['oss:oss_config:edit']"
          >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['oss:oss_config:remove']"
          >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['oss:oss_config:export']"
          >导出
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="Refresh"
          @click="handleRefresh"
          v-hasPermi="['oss:oss_config:list']"
          >重启引擎
        </el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="oss_configList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="id" />
      <el-table-column label="配置名称" align="center" prop="name" />
      <el-table-column label="存储类型" align="center" prop="ossType">
        <template v-slot="scope">
          <dict-tag :options="storage_type" :value="scope.row.ossType" />
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" key="status">
        <template v-slot="scope">
          <el-switch
            v-model="scope.row.status"
            active-value="0"
            inactive-value="1"
            @change="handleEngineChange(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template v-slot="scope">
          <el-button
            type="primary" link
            icon="Edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['oss:oss_config:edit']"
            >修改
          </el-button>
          <el-button
            type="primary" link
            icon="Delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['oss:oss_config:remove']"
            >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改存储配置对话框 -->
    <el-dialog :title="title" v-model="open" width="800px" append-to-body>
      <el-form ref="form" :model="ossConfigForm" :rules="rules" label-width="100px">
        <el-form-item label="配置名称" prop="name">
          <el-input v-model="ossConfigForm.name" placeholder="请输入配置名称" />
        </el-form-item>
        <el-form-item label="存储引擎" prop="ossType">
          <el-select
            v-model="ossConfigForm.ossType"
            placeholder="请选择存储引擎"
            clearable
            style="width: 100%"
          >
            <el-option
              v-for="dict in storage_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <template v-if="ossConfigForm.ossType == 'local'">
          <el-form-item label="访问域名">
            <el-input v-model="config.domain" placeholder="请输入访问所用的域名" />
            <span>域名请不要以/结尾</span>
          </el-form-item>
          <el-form-item label="默认桶">
            <el-input v-model="config.bucketName" placeholder="请输入默认桶名称" />
          </el-form-item>
          <el-form-item label="上传目录">
            <el-input v-model="config.filePath" placeholder="请输入上传文件根目录" />
          </el-form-item>
        </template>
        <template v-else-if="ossConfigForm.ossType == 'tencent_cos'">
          <el-form-item label="AppID">
            <el-input v-model="config.appId" placeholder="请输入访问用户身份ID" />
          </el-form-item>
          <el-form-item label="地域">
            <el-input v-model="config.region" placeholder="请输入COS地域" />
          </el-form-item>
          <el-form-item label="SecretID">
            <el-input v-model="config.secretId" placeholder="请输入访问密钥ID" />
          </el-form-item>
          <el-form-item label="SecretKey">
            <el-input v-model="config.secretKey" placeholder="请输入访问密钥Key" />
          </el-form-item>
          <el-form-item label="默认目录">
            <el-input v-model="config.bucketName" placeholder="请输入默认目录名称" />
          </el-form-item>
        </template>
        <template v-else-if="form.ossType == 'huawei_cloud_obs'">
          <el-form-item label="地域">
            <el-input v-model="config.region" placeholder="请输入COS地域"/>
          </el-form-item>
          <el-form-item label="节点">
            <el-input v-model="config.endPoint" placeholder="请输入OBS节点"/>
          </el-form-item>
          <el-form-item label="SecretID">
            <el-input v-model="config.secretId" placeholder="请输入访问密钥ID"/>
          </el-form-item>
          <el-form-item label="SecretKey">
            <el-input v-model="config.secretKey" placeholder="请输入访问密钥Key"/>
          </el-form-item>
          <el-form-item label="默认目录">
            <el-input v-model="config.bucketName" placeholder="请输入默认目录名称"/>
          </el-form-item>
        </template>
        <el-form-item label="是否启用" prop="status">
          <el-radio-group v-model="ossConfigForm.status">
            <el-radio
              v-for="dict in sys_normal_disable"
              :key="dict.value"
              :label="dict.value"
              >{{ dict.label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="ossConfigForm.remark" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <template v-slot:footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="Oss_config">
import {
  listOss_config,
  getOss_config,
  delOss_config,
  addOss_config,
  updateOss_config,
  changeStorageEngine,
  refreshEngine
} from '@/api/attachment/oss/oss_config'

const { proxy } = getCurrentInstance()
const { sys_normal_disable,storage_type } = proxy.useDict('sys_normal_disable', 'storage_type')
const oss_configList = ref([])
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const title = ref('')
const config = ref({})

const data = reactive({
  ossConfigForm: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    name: null,
    ossType: null
  },
  rules: {}
})

const { queryParams, ossConfigForm, rules } = toRefs(data)

/** 查询存储配置列表 */
function getList() {
  loading.value = true
  listOss_config(queryParams.value).then((response) => {
    oss_configList.value = response.rows
    total.value = response.total
    loading.value = false
  })
}
// 取消按钮
function cancel() {
  open.value = false
  reset()
}
// 表单重置
function reset() {
  ossConfigForm.value = {
    id: null,
    createTime: null,
    createBy: null,
    updateTime: null,
    updateBy: null,
    deleted: null,
    name: null,
    ossType: null,
    config: null,
    remark: null
  }
  proxy.resetForm('ossConfigForm')
}
/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1
  getList()
}
/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm('queryForm')
  handleQuery()
}
// 多选框选中数据
function handleSelectionChange(selection) {
  ids.value = selection.map((item) => item.id)
  single.value = selection.length !== 1
  multiple.value = !selection.length
}
/** 新增按钮操作 */
function handleAdd() {
  reset()
  open.value = true
  title.value = '添加存储配置'
}
/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const id = row.id || ids.value
  getOss_config(id).then((response) => {
    const data = response.data
    data.config = JSON.parse(data.config)
    ossConfigForm.value = data
    config.value = data.config
    open.value = true
    title.value = '修改存储配置'
  })
}
/** 提交按钮 */
function submitForm() {
  proxy.$refs['form'].validate((valid) => {
    if (valid) {
      ossConfigForm.value.config = JSON.stringify(config.value)
      if (ossConfigForm.value.id != null) {
        updateOss_config(ossConfigForm.value).then(() => {
          proxy.$modal.msgSuccess('修改成功')
          open.value = false
          getList()
        })
      } else {
        addOss_config(ossConfigForm.value).then(() => {
          proxy.$modal.msgSuccess('新增成功')
          open.value = false
          getList()
        })
      }
    }
  })
}
/** 删除按钮操作 */
function handleDelete(row) {
  const ids = row.id || ids.value
  proxy.$modal
    .confirm('是否确认删除存储配置编号为"' + ids + '"的数据项？')
    .then(function () {
      return delOss_config(ids)
    })
    .then(() => {
      getList()
      proxy.$modal.msgSuccess('删除成功')
    })
    .catch(() => {})
}
/** 导出按钮操作 */
function handleExport() {
  proxy.download(
    'oss/oss_config/export',
    {
      ...queryParams.value
    },
    `oss_config_${new Date().getTime()}.xlsx`
  )
}
// 切换引擎
function handleEngineChange(row) {
  if (row.status === '0') {
    proxy.$modal
      .confirm('确认要切换' + row.name + '"存储引擎吗？')
      .then(() => {
        return changeStorageEngine(row.id)
      })
      .then(() => {
        proxy.$modal.msgSuccess('引擎切换成功')
        getList()
      })
      .catch(function () {
        row.status = row.status === '0' ? '1' : '0'
      })
  } else {
    proxy.$modal.msgError(
      '至少保证有一个引擎正在工作！请直接选择需要启用的引擎，其它引擎将会自动关闭'
    )
    row.status = row.status === '1' ? '0' : '1'
  }
}
function handleRefresh() {
  refreshEngine().then(() => {
    proxy.$modal.msgSuccess('引擎重启成功')
  })
}
getList()

</script>
