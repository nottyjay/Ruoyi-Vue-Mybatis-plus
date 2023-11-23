<template>
  <div @click="handleClickItem" :class="checked === true ? 'selected attachment-icon' : 'attachment-icon' ">
    <div class="fileIconAll">
      <el-image
          v-if="isImage"
          :src="item.url" style="width: 120px;height: 120px"
      >
        <div slot="error" class="image-slot">
          <i class="el-icon-picture-outline"></i>
        </div>
      </el-image>
      <svg-icon v-else :icon-class="iconSelect" class="fileIcon"
                style="width: 120px;height: 120px;"
      />
      <div class="maskingOut" v-if="isEdit === true">
        <i class="el-icon-zoom-in" @click.stop="handlePreviewFile"></i>
        <i class="el-icon-download" @click.stop="handleDownloadFile"></i>
        <i class="el-icon-delete" @click.stop="handleDeleteFile"></i>
      </div>
      <div :class="checked === true ? 'checkBox' : 'checkBoxNone' " v-else>
        <!--        <el-checkbox v-model="checked"></el-checkbox>-->
        <i v-if="checked === true" class="el-icon-check check" v-model="checked"></i>
      </div>
    </div>
    <div class="fileName">{{ item.name }}</div>
  </div>
</template>

<script>
export default {
  props: {
    item: {
      type: Object,
      required: true
    },
    index: {
      type: Number,
      required: true
    },
    isEdit: {
      type: Boolean
    },
    isFileChecked: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      showPreview: false,
      previewImg: undefined,
      checked: this.isFileChecked,
      isChecked: false
    }
  },
  computed: {
    // 判断文件图标
    iconSelect() {
      switch (this.item.extension) {
        case 'pdf' :
          return 'icon_pdf'
        case 'txt' :
          return 'icon_txt'
        case 'doc' :
        case 'docx' :
          return 'icon_doc'
        case 'xls':
        case 'xlsx' :
          return 'icon_xlsx'
        case 'ppt' :
        case 'pptx':
          return 'icon_ppt'
        default:
          return ''
      }
    },
    isImage() {
      return this.item.extension === 'jpg' || this.item.extension === 'png' || this.item.extension === 'jpeg'
    }
  },
  watch: {
    checked: {
      handler(newVal) {
        if (newVal) {
          // byte Byte 16GB=16 * 1024
          this.$emit('checked', this.item)
        } else {
          this.$emit('unchecked', this.item)
        }
      },
      immediate: false
    }
  },
  methods: {
    // 预览文件
    handlePreviewFile() {
      this.$emit('on-filePreview', this.item)
    },
    // 删除文件
    handleDeleteFile() {
      this.$emit('on-fileDelete', this.item.id)
    },
    // 下载按钮
    handleDownloadFile() {
      window.open(this.item.url)
    },
    // 点击查看详情
    handleClickItem() {
      if (this.isEdit === true) {
        // 编辑模式下，点击时添加预览事件
        this.$emit('on-lookFile')
      } else {
        // 非点击事件时，修改checked的值
        this.checked = !this.checked
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.attachment-icon {
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

    .checkBox {
      width: 15px;
      height: 16px;
      display: block;
      position: absolute;
      top: -14px;
      left: -10px;
      background-color: #409EFF;
      //border: 1px solid #b9b9b9;
      border-radius: 2px;

      .check {
        color: #fff;
      }
    }

    .checkBoxNone {
      width: 15px;
      height: 16px;
      display: none;
      position: absolute;
      top: -14px;
      left: -10px;
      border-radius: 2px;
      background-color: #FFFFFF;

      .check {
        color: #409EFF;
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

.attachment-icon:hover {
  background-color: rgba(220, 216, 216, 0.2);

  .checkBox {
    display: block;
    color: #FFFFFF;
    border-radius: 2px;
  }

  .checkBoxNone {
    display: block;
    color: #FFFFFF;
    border-radius: 2px;
    border: 1px solid #cccccc;
  }
}

.selected {
  background-color: rgba(220, 216, 216, 0.2);
}

</style>
