<template>
  <!--  附件选择器-->
  <div>
    <el-button @click="handleOpenSelector" v-if="!readonly">选择附件</el-button>
    <!-- 被选中的附件 -->
    <div class="selectedAttachList">
      <div class="selectedAttachItem" v-for="(item, index) in selectedAttachmentList"
           :key="index"
      >
        <div class="selectedAttachItemName">{{ item.name }}</div>
        <template v-if="readonly">
          <el-button size="small" type="text" @click="preview(item)">预览</el-button>
          <el-button size="small" type="text" @click="download(item)">下载</el-button>
        </template>

        <i class="el-icon-close closed" @click="removeAttachment(index)" v-else></i>
      </div>
    </div>

    <el-dialog title="添加附件" :visible.sync="open" width="1000px" append-to-body>
      <attachment-panel :selected="this.selectedAttachmentList" v-if="open" :edit="false"
                        @selectedFileList="handleSelectedAttachmentList"
      ></attachment-panel>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

  </div>

</template>

<script>
import { getEnabledEngineConfig } from '@/api/attachment/oss/oss_config'
import { listAttachment, listAttachmentByIds } from '@/api/attachment/attachment'
import AttachmentPanel from '@/components/AttachmentPanel/index.vue'

export default {
  props: {
    value: {
      type: Array || Number
    },
    readonly: {
      type: Boolean,
      default: false
    }
  },
  components: {
    AttachmentPanel
  },
  data() {
    return {
      open: false,
      ossConfig: [],
      // 文件管理表格数据
      attachmentList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 20,
        createBy: null,
        name: null,
        storageType: null
      },
      // 被选中的附件按钮
      selectedAttachmentList: this.selectedList,
      // 父组件传下来的selectedList
      selectedList: []
    }
  },
  watch: {
    value: {
      handler: function(n) {
        if (n) {
          // 判断是否是数组，不是则变成数组
          let isArray = n instanceof Array
          if (isArray === false) {
            Array.of(n)
          }
          // 判断value是否为空，为空则清空，否则查找ID相同的item
          if (n.length > 0) {
            // 过滤出ID相同的item
            listAttachmentByIds(n).then(response => {
              this.selectedList = response.data
              this.selectedAttachmentList = this.selectedList
            })
          } else {
            this.selectedAttachmentList = []
          }
        } else {
          this.selectedAttachmentList = []
        }
      },
      immediate: true
    }
  },
  methods: {
    /** 查询文件管理列表 */
    getList() {
      getEnabledEngineConfig().then(response => {
        this.ossConfig = response.data
        return listAttachment(this.queryParams)
      }).then(response => {
        this.attachmentList = response.rows
      })
    },
    // 点击选择附件
    handleOpenSelector() {
      this.getList()
      this.open = true
    },
    // 点击确定
    submitForm() {
      // this.value = this.selectedAttachmentList
      let selectedId = []
      this.selectedAttachmentList.forEach((item, index) => {
        selectedId.push(item.id)
      })
      this.$emit('input', selectedId)
      // this.$emit('uploadSelectedFile', this.selectedAttachmentList)
      this.open = false
    },
    // 点击取消
    cancel() {
      this.open = false
      this.selectedAttachmentList = []
      this.value = []
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
    // 获取被选中的附件列表
    handleSelectedAttachmentList(value) {
      this.selectedAttachmentList = value
    },
    // 点击移除选中的某个文附件
    removeAttachment(index) {
      this.selectedAttachmentList.splice(index, 1)
      let selectedId = []
      this.selectedAttachmentList.forEach((item, index) => {
        selectedId.push(item.id)
      })
      this.$emit('deleteAttachment', selectedId)
    },
    preview(item) {
      window.open(item.url, '_blank')
    },
    download(item) {
      this.$download.downloadAttachment(item.id)
    }
  }
}
</script>

<style lang="scss" scoped>
.selectedAttachList {
  width: 320px;
  margin-top: 10px;

  .selectedAttachItem {
    width: 100%;
    height: 30px;
    display: flex;
    align-items: center;
    padding: 0 5px;
    margin-bottom: 5px;
    background-color: rgba(245, 241, 241, 30%);
    border: 1px solid rgba(245, 241, 241, 30%);
    border-radius: 5px;

    .selectedAttachItemName {
      flex: 1;
      line-height: 30px;
    }
  }

  .selectedAttachItem:hover {
    border: 1px dashed #ffac00;
    //border-radius: 5px;
  }

  .closed:hover {
    color: #ff7300;
  }
}
</style>
