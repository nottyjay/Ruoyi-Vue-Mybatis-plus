<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="用户组名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入用户组名称"
          clearable
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="openSelectUserGroup"
          v-hasPermi="['system:role:edit']"
        >添加用户组
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-circle-close"
          size="mini"
          :disabled="multiple"
          @click="cancelAuthUserGroupAll"
          v-hasPermi="['system:role:edit']"
        >批量取消授权
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-close"
          size="mini"
          @click="handleClose"
        >关闭
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="userGroupList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="用户组名称" prop="name" :show-overflow-tooltip="true"/>
      <el-table-column label="描述" prop="description" :show-overflow-tooltip="true"/>
      <el-table-column label="状态" prop="status" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="组成员数" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          {{ scope.row.memberUserIds != null ? scope.row.memberUserIds.length : 0 }}
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-circle-close"
            @click="cancelAuthUserGroup(scope.row)"
            v-hasPermi="['system:role:remove']"
          >取消授权
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
    <select-user-group ref="select" :roleId="queryParams.roleId" @ok="handleQuery"/>
  </div>
</template>

<script>
import { allocatedUserGroupList, authUserGroupCancel, authUserGroupCancelAll } from '@/api/system/role'
import selectUserGroup from './selectUserGroup'

export default {
  name: 'AuthUser',
  dicts: ['sys_normal_disable'],
  components: { selectUserGroup },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中用户组
      userGroupIds: [],
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 用户表格数据
      userGroupList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        roleId: undefined,
        name: undefined
      }
    }
  },
  created() {
    const roleId = this.$route.params && this.$route.params.roleId
    if (roleId) {
      this.queryParams.roleId = roleId
      this.getList()
    }
  },
  methods: {
    /** 查询授权用户列表 */
    getList() {
      this.loading = true
      allocatedUserGroupList(this.queryParams).then(response => {
          this.userGroupList = response.rows
          this.total = response.total
          this.loading = false
        }
      )
    },
    // 返回按钮
    handleClose() {
      const obj = { path: '/system/role' }
      this.$tab.closeOpenPage(obj)
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
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.userGroupIds = selection.map(item => item.id)
      this.multiple = !selection.length
    },
    /** 打开授权用户表弹窗 */
    openSelectUserGroup() {
      this.$refs.select.show()
    },
    /** 取消授权按钮操作 */
    cancelAuthUserGroup(row) {
      const roleId = this.queryParams.roleId
      this.$modal.confirm('确认要取消该用户组"' + row.userName + '"角色吗？').then(function() {
        return authUserGroupCancel({ groupId: row.id, roleId })
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('取消授权成功')
      }).catch(() => {
      })
    },
    /** 批量取消授权按钮操作 */
    cancelAuthUserGroupAll(row) {
      const roleId = this.queryParams.roleId
      const userGroupIds = this.userGroupIds.join(',')
      this.$modal.confirm('是否取消选中用户组授权数据项？').then(function() {
        return authUserGroupCancelAll({ roleId, userGroupIds })
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('取消授权成功')
      }).catch(() => {
      })
    }
  }
}
</script>
