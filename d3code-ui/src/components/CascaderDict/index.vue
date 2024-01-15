<template>
    <div class="cascader">
        <el-cascader ref="refCascader" :value="value" :options="optData" @change="changeDict"></el-cascader>
    </div>
</template>

<script>
export default {
    name: 'Cascader',
    props: {
        optData: Array
    },
    data() {
        return {
            value: []
        }
    },
    created(){
        
    },
    methods: {
        changeDict(e) {
            this.$emit('selectDict', e.join('$$$'))
        }
    }
}
</script>