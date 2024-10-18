<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:area:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-guide"
          size="mini"
          @click="handleImport"
          v-hasPermi="['system:area:import']"
        >从文件同步
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-sort"
          size="mini"
          @click="toggleExpandAll"
        >展开/折叠
        </el-button>
      </el-col>
      <div class="top-right-btn" style="width: 200px;">
        <el-progress v-if="isSync" :text-inside="true" :stroke-width="26" :percentage="syncProcess" status="success"
        ></el-progress>
      </div>
    </el-row>

    <el-table
      v-if="refreshTable"
      v-loading="loading"
      :data="areaList"
      row-key="id"
      :lazy="true"
      :load="loadTree"
      :default-expand-all="isExpandAll"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
    >
      <el-table-column label="地区名称" align="left" width="500" prop="name"/>
      <el-table-column label="级别" align="center" prop="level"/>
      <el-table-column label="地区码" align="center" prop="code"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:area:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-plus"
            @click="handleAdd(scope.row)"
            v-hasPermi="['system:area:add']"
          >新增
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:area:remove']"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加或修改地区管理对话框 -->
    <el-dialog title="从文件导入" :visible.sync="importOpen" width="500px" append-to-body>
      <el-form label-width="80px">
        <el-form-item label="文件">
          <div class="upload-file">
            <el-upload
              :action="uploadFileUrl"
              :before-upload="handleBeforeUpload"
              :limit="1"
              :on-error="handleUploadError"
              :on-exceed="handleExceed"
              :on-success="handleUploadSuccess"
              :show-file-list="false"
              :headers="headers"
              class="upload-file-uploader"
              ref="fileUpload"
            >
              <!-- 上传按钮 -->
              <el-button size="mini" type="primary">选取文件</el-button>
              <!-- 上传提示 -->
              <div class="el-upload__tip" slot="tip" v-if="showTip">
                请上传
                <template v-if="fileSize"> 大小不超过 <b style="color: #f56c6c">{{ fileSize }}MB</b></template>
                <template v-if="fileType"> 格式为 <b style="color: #f56c6c">{{ fileType.join('/') }}</b></template>
                的文件
              </div>
            </el-upload>
          </div>
        </el-form-item>
      </el-form>
    </el-dialog>

    <!-- 添加或修改地区管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-col :span="24" v-if="form.parentId !== 0">
          <el-form-item label="上级地区" prop="parentId">
            <treeselect v-model="form.parentId" :options="areaOptions" :normalizer="normalizer"
                        placeholder="选择上级地区"
            />
          </el-form-item>
        </el-col>
        <el-form-item label="地区名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入地区名称"/>
        </el-form-item>
        <el-form-item label="地区码" prop="code">
          <el-input v-model="form.code" placeholder="请输入地区码"/>
        </el-form-item>
        <el-form-item label="顺序" prop="orderNum">
          <el-input v-model="form.orderNum" placeholder="请输入顺序" type="number"/>
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
import { listArea, getArea, delArea, addArea, updateArea, syncProcess } from '@/api/system/area'
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import { getToken } from '@/utils/auth'

export default {
  name: 'Area',
  components: {
    Treeselect
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 地区管理表格数据
      areaList: [],
      // 地区管理树选项
      areaOptions: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      importOpen: false,
      // 是否展开，默认全部展开
      isExpandAll: false,
      // 重新渲染表格状态
      refreshTable: true,
      // 查询参数
      queryParams: {
        name: null,
        level: null,
        code: null,
        parentName: null,
        ancestors: null,
        orderNum: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      isShowTip: true,
      fileType: ['json'],
      fileSize: 20,
      baseUrl: process.env.VUE_APP_BASE_API,
      uploadFileUrl: process.env.VUE_APP_BASE_API + '/system/area/importFile', // 上传的图片服务器地址
      headers: {
        Authorization: 'Bearer ' + getToken()
      },
      isSync: false,
      syncProcess: 0,
      syncInterval: null
    }
  },
  computed: {
    // 是否显示提示
    showTip() {
      return this.isShowTip && (this.fileType || this.fileSize)
    }
  },
  created() {
    this.getList()
  },
  beforeDestroy() {
    if (this.syncInterval) {
      clearInterval(this.syncInterval)
      this.syncInterval = false
    }
  },
  methods: {
    /** 查询地区管理列表 */
    getList() {
      this.loading = true
      if (this.queryParams.parentId == null) {
        this.queryParams.parentId = 0
      }
      listArea(this.queryParams).then(response => {
        this.areaList = this.handleTree(response.data, 'id', 'parentId').map(item => {
          item.hasChildren = true
          item.children = []
          return item
        })
        this.loading = false
      })
    },
    /** 转换地区管理数据结构 */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children
      }
      return {
        id: node.id,
        label: node.name,
        children: node.children
      }
    },
    /** 查询地区管理下拉树结构 */
    getTreeselect() {
      listArea().then(response => {
        this.areaOptions = []
        const data = { id: 0, name: '顶级节点', children: [] }
        data.children = this.handleTree(response.data, 'id', 'parentId')
        this.areaOptions.push(data)
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
        level: null,
        code: null,
        parentName: null,
        parentId: null,
        parentCode: null,
        ancestors: null,
        orderNum: null,
        remark: null
      }
      this.resetForm('form')
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    /** 新增按钮操作 */
    handleAdd(row) {
      this.reset()
      this.getTreeselect()
      if (row != null && row.id) {
        this.form.parentId = row.id
      } else {
        this.form.parentId = 0
      }
      this.open = true
      this.title = '添加地区管理'
    },
    /** 展开/折叠操作 */
    toggleExpandAll() {
      this.refreshTable = false
      this.isExpandAll = !this.isExpandAll
      this.$nextTick(() => {
        this.refreshTable = true
      })
    },
    handleImport() {
      this.importOpen = true

    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      this.getTreeselect()
      if (row != null) {
        this.form.parentId = row.id
      }
      getArea(row.id).then(response => {
        this.form = response.data
        this.open = true
        this.title = '修改地区管理'
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateArea(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addArea(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除地区管理编号为"' + row.id + '"的数据项？').then(function() {
        return delArea(row.id)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    // 上传前校检格式和大小
    handleBeforeUpload(file) {
      // 校检文件类型
      if (this.fileType) {
        const fileName = file.name.split('.')
        const fileExt = fileName[fileName.length - 1]
        const isTypeOk = this.fileType.indexOf(fileExt) >= 0
        if (!isTypeOk) {
          this.$modal.msgError(`文件格式不正确, 请上传${this.fileType.join('/')}格式文件!`)
          return false
        }
      }
      // 校检文件大小
      if (this.fileSize) {
        const isLt = file.size / 1024 / 1024 < this.fileSize
        if (!isLt) {
          this.$modal.msgError(`上传文件大小不能超过 ${this.fileSize} MB!`)
          return false
        }
      }
      this.$modal.loading('正在上传文件，请稍候...')
      this.isSync = false
      this.syncProcess = 0
      if (this.syncInterval) {
        clearInterval(this.syncInterval)
        this.syncInterval = false
      }
      return true
    },
    // 文件个数超出
    handleExceed() {
      this.$modal.msgError(`上传文件数量不能超过 ${this.limit} 个!`)
    },
    // 上传失败
    handleUploadError(err) {
      this.$modal.msgError('上传文件失败，请重试')
      this.$modal.closeLoading()
    },
    // 上传成功回调
    handleUploadSuccess(res, file) {

      if (res.code === 200) {
        this.$modal.closeLoading()
        this.uploadedSuccessfully()
      } else {
        this.$modal.closeLoading()
        this.$modal.msgError(res.msg)
        this.$refs.fileUpload.handleRemove(file)
        this.uploadedSuccessfully()
      }
    },
    // 上传结束处理
    uploadedSuccessfully() {
      this.isSync = true
      this.importOpen = false
      const that = this
      this.syncInterval = setInterval(() => {
        syncProcess().then(response => {
          this.syncProcess = parseInt(response.data)
          if (this.syncProcess > 99) {
            clearInterval(that.syncInterval)
            that.syncInterval = false
            that.isSync = false
            that.syncProcess = 0
            that.$modal.msgSuccess('地区信息同步完成')
            that.getList()
          }
        })
      }, 1000)
    },
    loadTree(tree, treeNode, resolve) {
      this.queryParams.parentId = tree.id
      listArea(this.queryParams).then(response => {
        let data = this.handleTree(response.data, 'id', 'parentId').map(item => {
          item.hasChildren = true
          item.children = []
          return item
        })
        resolve(data)
      })
    }
  }
}
</script>
