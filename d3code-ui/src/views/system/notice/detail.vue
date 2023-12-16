<template>
  <div class="app-container">
    <section>
      <div style="display: flex; justify-content: center; font-size: 24px;"><h1>{{ notice.noticeTitle }}</h1></div>
      <div style="display: flex; justify-content: center; font-size: 18px"><h4>
        {{ parseTime(notice.createTime, '{y}年{m}月{d}日') }}
        作者：{{ notice.createBy }}</h4></div>
    </section>
    <el-divider></el-divider>
    <section>
      <div style="display: flex; justify-content: center; font-size: 18px;">
        <div style="width: 1000px;" v-html="notice.noticeContent"></div>
      </div>
    </section>
  </div>
</template>

<script>
import { getNotice } from '@/api/system/notice'

export default {
  data() {
    return {
      notice: null,
      id: null
    }
  },
  created() {
    this.$nextTick(() => {
      this.id = this.$route.params.id
      getNotice(this.id).then(response => {
        this.notice = response.data
      })
    })
  }
}
</script>
