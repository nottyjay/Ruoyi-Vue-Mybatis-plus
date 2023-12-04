<template>
  <div class="user_input">
    <span v-if="readonly">{{ selectUser[labelKey] }}</span>
    <el-autocomplete v-else v-model="name" :value-key="labelKey"
                     :fetch-suggestions="querySearchAsync"
                     :placeholder="placeholder"
                     @select="handleSelect">
      <template v-slot="scope">
        {{ scope.item.nickName }} [{{ scope.item.userName }}]
      </template>
    </el-autocomplete>
  </div>
</template>

<script>
import {getUser, listUser} from "@/api/system/user";

export default {
  props: {
    value: {
      type: Number,
      default: null
    },
    readonly: {
      type: Boolean,
      default: false
    },
    labelKey: {
      type: String,
      default: 'nickName'
    },
    placeholder: {
      type: String,
      default: '请选择用户'
    }
  },
  data() {
    return {
      selectUser: null,
      name: null
    }
  },
  watch: {
    value: {
      handle(newVal) {
        if (this.selectUser === null && this.selectUser.id !== newVal) {
          this.getUser()
        }
      },
      immediate: true
    }
  },
  methods: {
    getUser() {
      getUser(this.value).then(response => {
        this.selectUser = response.data
      })
    },
    querySearchAsync(name, cb) {
      listUser({nickName: name, pageSize: 20, pageNum: 1}).then(response => {
        this.userList = response.rows
        cb(this.userList)
      })
    },
    handleSelect(item) {
      this.selectUser = item
      this.value = item.userId
      this.$emit('input', item.userId)
    }
  }
}
</script>
