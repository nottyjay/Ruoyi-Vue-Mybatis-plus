<template>
  <el-cascader :props="props"></el-cascader>
</template>

<script>
import { listArea } from '@/api/system/area'

export default {
  name: 'AreaCascader',
  data() {
    return {
      props: {
        lazy: true,
        lazyLoad(node, resolve) {
          console.log(node)
          const code = node.root ? 0 : node.value
          listArea({ parentCode: code }).then(res => {
            let data = res.data.map(item => {
              return {
                label: item.name,
                value: item.code,
                leaf: !item.hasChildren
              }
            })
            resolve(data)
          })
        }
      }
    }
  }
}
</script>
