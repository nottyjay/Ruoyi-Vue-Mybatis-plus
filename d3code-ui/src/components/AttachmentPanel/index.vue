<template>
  <!-- 附件面板 -->
  <div class="attachment-panel">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="文件名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入文件名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 附件列表-->
    <div class="attachment-panelList">
      <slot></slot>
      <!-- 所有文件-->
      <div style="width: 100%;box-shadow: 0 0 5px 6px rgba(141,141,141,0.2) inset;margin-top: 20px;padding: 15px;"
           v-loading="loading"
      >
        <div style="display: flex;flex-wrap: wrap;">
          <attachment-item v-for="(item, index) in attachmentList" :key="index" :item="item"
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
        :visible.sync="showPreview"
        width="60%"
        center
      >
        <img :src="previewImg" alt="" style="width: 100%;height: 100%;">
      </el-dialog>
    </div>
  </div>
</template>

<script>
import AttachmentItem from '../AttachmentSelector/AttachmentItem/index.vue'
import { getEnabledEngineConfig } from '../../api/attachment/oss/oss_config'
import { listAttachment } from '../../api/attachment/attachment'

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
  components: { AttachmentItem },
  data() {
    return {
      // 遮罩层
      loading: true,
      ossConfig: [],
      // 文件管理表格数据
      attachmentList: [],
      // 总条数
      total: 0,
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
      showSearch: true
    }
  },
  created() {
    this.getList()
  },
  watch: {
    selectedAttachmentList: {
      handler: function() {
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
        this.total = response.total
        this.loading = false
      })
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
      this.$modal.confirm('是否确认删除此文件？').then(function() {
        return ''
      }).then(() => {
        this.attachmentList.splice(index, 1)
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
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
