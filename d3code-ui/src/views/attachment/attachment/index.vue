<template>
  <div class="app-container">
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
    <el-button
      type="primary"
      plain
      icon="el-icon-plus"
      size="mini"
      @click="handleAdd"
      v-hasPermi="['attachment:attachment:add']"
    >新增
    </el-button>

    <!--  附件列表  -->
    <div style="width: 100%;" v-loading="loading">
      <div style="display: flex;flex-wrap: wrap">
        <div v-for="(item, index) in attachmentList" :key="index" class="fileList" @click="fileInformation(item)">
          <div class="fileIconAll">
            <el-image v-if="item.extension === 'jpg' || item.extension === 'png' || item.extension === 'jpeg' "
                      :src="item.url" style="width: 120px;height: 120px"
            >
              <div slot="error" class="image-slot">
                <i class="el-icon-picture-outline"></i>
              </div>
            </el-image>
            <svg-icon v-else :icon-class="handleFileImg(item)" class="fileIcon"/>
            <div class="maskingOut">
              <i class="el-icon-zoom-in" @click.stop="handlePreviewFile(item)"></i>
              <i class="el-icon-download" @click.stop="handleDownloadFile(item)"></i>
              <i class="el-icon-delete" @click.stop="handleDeleteFile(index)"></i>
            </div>
          </div>
          <div class="fileName">{{ item.name }}</div>
        </div>
      </div>
    </div>

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
      <img :src="previreImg" alt="" style="width: 100%;height: 100%;">
    </el-dialog>


    <!-- 添加或修改文件管理对话框-->
    <attachment-upload :visible.sync="open" @on-success="handleUploadSuccess"/>

    <!-- 附件详情信息 -->
    <el-drawer
      title="我是标题"
      :visible.sync="drawer"
      :with-header="false"
      size="600px"
    >
      <div style="font-size: 18px;padding: 20px 20px 0;font-weight: bold">文件详情</div>
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
            <dict-tag :options="dict.type.storage_type" :value="form.storageType"/>
          </el-form-item>
          <el-form-item label="存储路径">
            <span>{{ form.path }}</span>
          </el-form-item>
          <el-form-item label="外网访问路径">
            <div style="display:flex">
              <span>{{ form.url }}</span>
              <div>
                <el-link :underline="false" v-clipboard:copy="form.url"
                         v-clipboard:success="clipboardSuccess"
                >
                  <i class="el-icon-document-copy" style="font-size: 20px"/>
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
import {
  listAttachment,
  getAttachment,
  delAttachment,
  addAttachment,
  updateAttachment
} from '@/api/attachment/attachment'
import { getEnabledEngineConfig } from '@/api/attachment/oss/oss_config'
import { getToken } from '@/utils/auth'
import AttachmentUpload from '@/components/AttachmentUpload/index.vue'

export default {
  name: 'Attachment',
  components: {
    AttachmentUpload
  },
  dicts: ['storage_type'],
  data() {
    return {
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
      previreImg: null,
      uploadFileUrl: process.env.VUE_APP_BASE_API + '/common/upload', // 上传的图片服务器地址
      headers: {
        Authorization: 'Bearer ' + getToken()
      },
      drawer: false
    }
  },
  created() {
    this.getList()
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
    // 判断文件图标
    handleFileImg(item) {
      if (item.extension === 'doc') {
        return 'icon_doc'
      } else if (item.extension === 'pdf') {
        return 'icon_pdf'
      } else if (item.extension === 'ppt') {
        return 'icon_ppt'
      } else if (item.extension === 'txt') {
        return 'icon_txt'
      } else if (item.extension === 'docx') {
        return 'icon_word'
      } else if (item.extension === 'xlsx') {
        return 'icon_xlsx'
      } else {
        return ''
      }
      // switch (item.extension){
      //   case 'doc' : return 'icon_doc'; break;
      //   case 'pdf' : return 'icon_pdf'; break;
      //   case 'ppt' : return 'icon_ppt'; break;
      //   case 'txt' : return 'icon_txt'; break;
      //   case 'docx' : return 'icon_word'; break;
      //   case 'xlsx' : return 'icon_xlsx'; break;
      // }
    },
    // 预览文件
    handlePreviewFile(item) {
      if (item.extension === 'jpg' || item.extension === 'png' || item.extension === 'jpeg') {
        this.showPreview = true
        this.previreImg = item.url
      } else {
        console.log('qqqqq')
        // 跳转新页面
      }
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
    // 点击附件详情
    fileInformation(item) {
      console.log(item, '------item')
      this.drawer = true
      this.form = item
    },
    clipboardSuccess() {
      this.$modal.msgSuccess('复制成功')
    },
    // 下载按钮
    handleDownloadFile(item) {
      window.open(item.url)
    }
  }
}
</script>

<style lang="scss" scoped>
.fileList {
  list-style: none;
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 140px;
  height: 100%;
  margin: 10px 20px;
  padding: 10px;
  position: relative;

  .fileIconAll {
    margin-top: 5px;
    position: relative;

    .fileIcon {
      width: 70px;
      height: 70px;
    }

    .maskingOut {
      width: 100%;
      height: 100%;
      background-color: rgba(0, 0, 0, 0.4);
      position: absolute;
      top: 0;
      opacity: 0;
      display: flex;
      justify-content: space-around;
      align-items: center;

      .el-icon-zoom-in {
        font-size: 20px;
        color: #FFFFFF;
        z-index: 9;
      }

      .el-icon-delete {
        font-size: 20px;
        color: #FFFFFF;
        z-index: 9;
      }

      .el-icon-download {
        font-size: 20px;
        color: #FFFFFF;
        z-index: 9;
      }
    }
  }

  .fileIconAll:hover {

    .maskingOut {
      opacity: 1;
    }

    .el-icon-delete {
      display: block;
    }

    .el-icon-zoom-in {
      display: block;
    }

  }

  .fileName {
    word-break: break-all;
    margin-top: 5px;
    text-align: center;
    font-size: 13px
  }
}

.fileList:hover {
  background-color: rgba(220, 216, 216, 0.2);
}

.upload-demo {
  text-align: center;
}

</style>
