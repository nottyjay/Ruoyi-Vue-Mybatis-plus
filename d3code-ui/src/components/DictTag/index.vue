<template>
  <div>
    <!-- 一级字典 -->
    <template v-if="!isCascader">
      <template v-for="(item, index) in options">
        <template v-if="values.includes(item.value)">
          <span v-if="item.raw.listClass == 'default' || item.raw.listClass == ''" :key="item.value" :index="index"
            :class="item.raw.cssClass">{{ item.label }}</span>
          <el-tag v-else :disable-transitions="true" :key="item.value" :index="index"
            :type="item.raw.listClass == 'primary' ? '' : item.raw.listClass" :class="item.raw.cssClass">
            {{ item.label }}
          </el-tag>
        </template>
      </template>
    </template>
    <!-- 多级字典 -->
    <template v-else v-for="(item, index) in cascaderOptions">
      <span v-if="item.raw.listClass == 'default' || item.raw.listClass == ''" :key="item.value" :index="index"
        :class="item.raw.cssClass">{{ item.label }}</span>
      <el-tag v-else :disable-transitions="true" :key="item.value" :index="index"
        :type="item.raw.listClass == 'primary' ? '' : item.raw.listClass" :class="item.raw.cssClass">
        {{ item.label }}
      </el-tag>
    </template>

  </div>
</template>

<script>
export default {
  name: "DictTag",
  props: {
    options: {
      type: Array,
      default: null,
    },
    value: [Number, String, Array],
  },
  computed: {
    isCascader() {
      switch (this.value.indexOf('$$$')) {
        case -1:
          return false
        default:
          return true
      }
    },
    cascaderValue() {
      return this.value.split('$$$')
    },
    values() {
      if (this.value !== null && typeof this.value !== 'undefined') {
        return Array.isArray(this.value) ? this.value : [String(this.value)];
      } else {
        return [];
      }
    },
    cascaderOptions() {
      var arr = []
      var op = this.options
      for (var index = 0; index < this.cascaderValue.length; index++) {
        for (var i = 0; i < op.length; i++) {
          if (this.cascaderValue[index] === op[i].value) {
            arr.push(op[i])
            op = op[i].children
            if (op === null) {
              return arr
            }
          }
        }
      }
    }
  }
};
</script>
<style scoped>
.el-tag + .el-tag {
  margin-left: 10px;
}
</style>