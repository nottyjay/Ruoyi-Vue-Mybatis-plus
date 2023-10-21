<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="配置名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入配置名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['oss:oss_config:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
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
          icon="el-icon-delete"
          size="mini"
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
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['oss:oss_config:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="oss_configList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="序号" align="center" prop="id"/>
      <el-table-column label="配置名称" align="center" prop="name"/>
      <el-table-column label="存储类型" align="center" prop="ossType">
        <template v-slot="scope">
          <dict-tag :options="dict.type.storage_type" :value="scope.row.ossType"/>
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
      <el-table-column label="备注" align="center" prop="remark"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['oss:oss_config:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['oss:oss_config:remove']"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改存储配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="配置名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入配置名称"/>
        </el-form-item>
        <el-form-item label="存储引擎" prop="ossType">
          <el-select v-model="form.ossType" placeholder="请选择存储引擎" clearable style="width: 100%">
            <el-option v-for="dict in dict.type.storage_type" :key="dict.value" :label="dict.label"
                       :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <template v-if="form.ossType == 'local'">
          <el-form-item label="访问域名">
            <el-input v-model="config.domain" placeholder="请输入访问所用的域名"/>
            <span>域名请不要以/结尾</span>
          </el-form-item>
          <el-form-item label="默认桶">
            <el-input v-model="config.bucketName" placeholder="请输入默认桶名称"/>
          </el-form-item>
          <el-form-item label="上传目录">
            <el-input v-model="config.filePath" placeholder="请输入上传文件根目录"/>
          </el-form-item>
        </template>
        <template v-else-if="form.ossType == 'tencent_cos'">
          <el-form-item label="AppID">
            <el-input v-model="config.appId" placeholder="请输入访问用户身份ID"/>
          </el-form-item>
          <el-form-item label="地域">
            <el-input v-model="config.region" placeholder="请输入COS地域"/>
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
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in dict.type.sys_normal_disable"
              :key="dict.value"
              :label="dict.value"
            >{{ dict.label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listOss_config,
  getOss_config,
  delOss_config,
  addOss_config,
  updateOss_config,
  changeStorageEngine
} from '@/api/attachment/oss/oss_config'

export default {
  name: 'Oss_config',
  dicts: ['storage_type', 'sys_normal_disable'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 存储配置表格数据
      oss_configList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        ossType: null
      },
      // 表单参数
      form: {},
      // 配置参数
      config: {},
      // 表单校验
      rules: {}
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询存储配置列表 */
    getList() {
      this.loading = true
      listOss_config(this.queryParams).then(response => {
        this.oss_configList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
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
        ossType: null,
        config: null,
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
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '添加存储配置'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getOss_config(id).then(response => {
        const data = response.data
        data.config = JSON.parse(data.config)
        this.form = data
        this.config = data.config
        this.open = true
        this.title = '修改存储配置'
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          this.form.config = JSON.stringify(this.config)
          if (this.form.id != null) {
            updateOss_config(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addOss_config(this.form).then(response => {
              this.$modal.msgSuccess('新增成功')
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal.confirm('是否确认删除存储配置编号为"' + ids + '"的数据项？').then(function() {
        return delOss_config(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('oss/oss_config/export', {
        ...this.queryParams
      }, `oss_config_${new Date().getTime()}.xlsx`)
    },
    // 切换引擎
    handleEngineChange(row) {
      if (row.status === '0') {
        const _this = this
        this.$modal.confirm('确认要切换' + row.name + '"存储引擎吗？').then(() => {
          return changeStorageEngine(row.id)
        }).then(() => {
          this.$modal.msgSuccess('引擎切换成功')
          _this.getList()
        }).catch(function() {
          row.status = row.status === '0' ? '1' : '0'
        })
      } else {
        this.$modal.msgError('至少保证有一个引擎正在工作！请直接选择需要启用的引擎，其它引擎将会自动关闭')
        row.status = row.status === '1' ? '0' : '1'
      }
    }
  }
}
</script>
