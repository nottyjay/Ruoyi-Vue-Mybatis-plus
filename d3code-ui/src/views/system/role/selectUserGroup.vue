<template>
  <!-- 授权用户 -->
  <el-dialog title="选择用户组" :visible.sync="visible" width="800px" top="5vh" append-to-body>
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true">
      <el-form-item label="用户名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入用户名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-row>
      <el-table @row-click="clickRow" ref="table" :data="userGroupList" @selection-change="handleSelectionChange"
                height="260px"
      >
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column label="编号" align="center" prop="id"/>
        <el-table-column label="组名" align="center" prop="name"/>
        <el-table-column label="描述" align="center" prop="description" width="200"/>
        <el-table-column label="状态" align="center" prop="status">
          <template v-slot="scope">
            <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
          </template>
        </el-table-column>
        <el-table-column label="创建时间" align="center" prop="createTime" width="180">
          <template v-slot="scope">
            <span>{{ parseTime(scope.row.createTime) }}</span>
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
    </el-row>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="handleSelectUserGroup">确 定</el-button>
      <el-button @click="visible = false">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { authUserGroupSelectAll, unallocatedUserGroupList } from '@/api/system/role'

export default {
  dicts: ['sys_normal_disable'],
  props: {
    // 角色编号
    roleId: {
      type: [Number, String]
    }
  },
  data() {
    return {
      // 遮罩层
      visible: false,
      // 选中数组值
      userGroupIds: [],
      // 总条数
      total: 0,
      // 未授权用户数据
      userGroupList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        roleId: undefined,
        userName: undefined
      }
    }
  },
  methods: {
    // 显示弹框
    show() {
      this.queryParams.roleId = this.roleId
      this.getList()
      this.visible = true
    },
    clickRow(row) {
      this.$refs.table.toggleRowSelection(row)
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.userGroupIds = selection.map(item => item.id)
    },
    // 查询表数据
    getList() {
      unallocatedUserGroupList(this.queryParams).then(res => {
        this.userGroupList = res.rows
        this.total = res.total
      })
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
    /** 选择授权用户操作 */
    handleSelectUserGroup() {
      const roleId = this.queryParams.roleId
      const userGroupIds = this.userGroupIds.join(',')
      if (userGroupIds == '') {
        this.$modal.msgError('请选择要分配的用户组')
        return
      }
      authUserGroupSelectAll({ roleId, userGroupIds }).then(res => {
        this.$modal.msgSuccess(res.msg)
        if (res.code === 200) {
          this.visible = false
          this.$emit('ok')
        }
      })
    }
  }
}
</script>
