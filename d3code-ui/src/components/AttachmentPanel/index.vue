<template>
  <!-- 附件面板 -->
  <div class="attachment-panel">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="文件名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入文件名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search"  @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh"  @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 附件列表-->
    <div class="attachment-panelList">
      <el-button
        type="primary"
        plain
        icon="Plus"
        @click="handleAdd"
        v-hasPermi="['attachment:attachment:add']"
      >新增
      </el-button>

      <!-- 所有文件-->
      <div style="width: 100%;box-shadow: 0 0 5px 6px rgba(141,141,141,0.2) inset;margin-top: 20px;padding: 15px;"
           v-loading="loading"
      >
        <div
          :style="isFileNone === false ? 'display: flex;flex-wrap: wrap;' : 'display: flex;flex-wrap: wrap;justify-content: center;'"
        >
          <el-empty v-if="isFileNone === true" description="暂无数据" :image-size="120"></el-empty>
          <attachment-item v-else v-for="(item, index) in attachmentList" :key="index" :item="item"
                           :index="index"
                           :is-edit="isEdit"
                           :is-file-checked="checkFileSelected(item)"
                           @checked="handleItemChecked"
                           @unchecked="handleItemUncheck"
                           @on-fileDelete="handleDeleteFile(index)"
                           @on-filePreview="handlePreviewFile(item)"
                           @on-lookFile="fileInformation(item)"
          />
        </div>
      </div>

      <!-- 分页 -->
      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />

      <!-- 预览对话框 -->
      <el-dialog
        v-model="showPreview"
        width="60%"
        center
      >
        <img :src="previewImg" alt="" style="width: 100%;height: 100%;">
      </el-dialog>
    </div>

    <!-- 添加或修改文件管理对话框-->
    <attachment-upload v-model:visible="open" @on-success="handleUploadSuccess"/>
  </div>
</template>

<script>
import AttachmentItem from './AttachmentItem/index.vue'
import {getEnabledEngineConfig} from '@/api/attachment/oss/oss_config'
import {delAttachment, listAttachment} from '@/api/attachment/attachment'
import AttachmentUpload from "@/components/AttachmentUpload/index.vue";

export default {
  props: {
    isEdit: {
      type: Boolean
    },
    selected: {
      type: Array,
      default: () => []
    }
  },
  components: {AttachmentUpload, AttachmentItem},
  data() {
    return {
      // 遮罩层
      loading: true,
      ossConfig: [],
      // 文件管理表格数据
      attachmentList: [],
      // 总条数
      total: 0,
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
      showPreview: false,
      previewImg: null,
      selectedAttachmentList: this.selected,
      // 显示搜索条件
      showSearch: true,
      // 判断文件列表是否为空
      isFileNone: false
    }
  },
  created() {
    this.getList()
  },
  watch: {
    selectedAttachmentList: {
      handler: function () {
        this.$emit('selectedFileList', this.selectedAttachmentList)
      }
    }
  },
  methods: {
    /** 查询文件管理列表 */
    getList() {
      this.loading = true
      getEnabledEngineConfig().then(response => {
        this.ossConfig = response.data
        return listAttachment(this.queryParams)
      }).then(response => {
        this.attachmentList = response.rows
        if (this.attachmentList.length === 0) {
          this.isFileNone = true
        }
        this.isFileNone = false
        this.total = response.total
        this.loading = false
      })
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
        storageType: null,
        path: null,
        remark: null
      }
      this.resetForm('form')
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
    checkFileSelected(item) {
      const index = this.selectedAttachmentList.findIndex((attachment) => {
        return attachment.id === item.id
      })
      return index !== -1
    },
    // 点击附件详情
    fileInformation(item) {
      this.$emit('askFileInformation', item)
    },
    // 删除文件
    handleDeleteFile(index) {
      const id = this.attachmentList[index].id
      this.$modal.confirm('是否确认删除此文件？').then(function () {
        return delAttachment(id)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch((e) => {
        console.log(e)
      })
    },
    // 预览文件
    handlePreviewFile(item) {
      if (item.extension === 'jpg' || item.extension === 'png' || item.extension === 'jpeg') {
        this.showPreview = true
        this.previewImg = item.url
      } else {
        // 跳转新页面
      }
    },
    // 下载按钮
    handleDownloadFile(item) {
      window.open(item.url)
    },
    // 选中文件
    handleItemChecked(item) {
      this.selectedAttachmentList.push(item)
    },
    // 取消选中
    handleItemUncheck(uncheckedItem) {
      this.selectedAttachmentList.forEach((item, index) => {
        if (uncheckedItem.id === item.id) {
          this.selectedAttachmentList.splice(index, 1)
        }
      })
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
    }
  }

}
</script>
