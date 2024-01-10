<template>
    <div class="cascader">
        <el-cascader :options="optData"></el-cascader>
    </div>
</template>

<script>
export default {
    name: 'Cascader',
    props: {
        optData: Array
    },
    data() {
        return {}
    }
}
</script>