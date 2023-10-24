<template>
  <div class="app-container">
    <!-- 搜索工作栏 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="组名" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入组名" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option v-for="dict in dict.type.sys_normal_disable"
                     :key="dict.value" :label="dict.label" :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="创建时间" prop="createTime">
        <el-date-picker v-model="queryParams.createTime" style="width: 240px" value-format="yyyy-MM-dd HH:mm:ss"
                        type="daterange"
                        range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"
                        :default-time="['00:00:00', '23:59:59']"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 操作工具栏 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
                   v-hasPermi="['system:userGroup:add']"
        >新增
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 列表 -->
    <el-table v-loading="loading" :data="list">
      <el-table-column label="编号" align="center" prop="id"/>
      <el-table-column label="组名" align="center" prop="name"/>
      <el-table-column label="描述" align="center" prop="description" width="200"/>
      <el-table-column label="状态" align="center" prop="status">
        <template v-slot="scope">
          <el-switch
            v-model="scope.row.status"
            active-value="0"
            inactive-value="1"
            @change="handleStatusChange(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template v-slot="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template v-slot="scope">
          <el-button size="mini" type="text" icon="el-icon-search" @click="handleInfo(scope.row)"
                     v-hasPermi="['system:userGroup:query']"
          >查看
          </el-button>
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
                     v-hasPermi="['system:userGroup:update']"
          >修改
          </el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['system:userGroup:delete']"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
                @pagination="getList"
    />

    <!-- 对话框(添加 / 修改) -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body :close-on-click-modal="false">
      <template v-if="readonly">
        <el-descriptions :column="2" border>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-user"></i>
              组名
            </template>
            {{ form.name }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-mobile-phone"></i>
              状态
            </template>
            <dict-tag :options="dict.type.sys_normal_disable" :value="form.status"/>
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-location-outline"></i>
              描述
            </template>
            {{ form.description }}
          </el-descriptions-item>
        </el-descriptions>
        <el-divider>用户组信息</el-divider>
        <el-table :data="form.users" :max-height="400">
          <el-table-column label="用户名" prop="userName"/>
          <el-table-column label="昵称" prop="nickName"/>
          <el-table-column label="手机" prop="phonenumber"/>
          <el-table-column label="邮箱" prop="email"/>
          <el-table-column label="部门" prop="dept.deptName"/>
          <el-table-column label="状态">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
            </template>
          </el-table-column>
        </el-table>
        <pagination v-show="userTotal > 0" :total="userTotal" :page.sync="groupQueryParams.pageNum"
                    :limit.sync="groupQueryParams.pageSize"
                    @pagination="listUserByGroup"
        />
      </template>
      <template v-else>
        <el-form ref="form" :model="form" :rules="rules" label-width="80px">
          <el-row>
            <el-col :span="12">
              <el-form-item label="组名" prop="name">
                <el-input v-model="form.name" placeholder="请输入组名"/>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="状态" prop="status">
                <el-radio-group v-model="form.status">
                  <el-radio v-for="dict in dict.type.sys_normal_disable"
                            :key="dict.value" :label="dict.value"
                  >{{ dict.label }}
                  </el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item label="描述" prop="description">
                <el-input v-model="form.description" placeholder="请输入描述"/>
              </el-form-item>
            </el-col>
            <template v-if="form.id != null">
              <el-col :span="24">
                <el-divider>成员列表</el-divider>
              </el-col>
              <el-col :span="24">
                <el-table :data="form.users" :max-height="400">
                  <el-table-column label="用户名" prop="userName"/>
                  <el-table-column label="昵称" prop="nickName"/>
                  <el-table-column label="手机" prop="phonenumber"/>
                  <el-table-column label="邮箱" prop="email"/>
                  <el-table-column label="部门" prop="dept.deptName"/>
                  <el-table-column label="状态">
                    <template slot-scope="scope">
                      <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
                    </template>
                  </el-table-column>
                  <el-table-column>
                    <template slot="header" slot-scope="scope">
                      <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAddUserInGroup"
                                 v-hasPermi="['system:userGroup:add']"
                      >新增
                      </el-button>
                    </template>
                    <template v-slot="scope">
                      <el-button size="mini" type="text" icon="el-icon-delete"
                                 @click="handleUserDeleteInGroup(scope.row)"
                                 v-hasPermi="['system:userGroup:delete']"
                      >删除
                      </el-button>
                    </template>
                  </el-table-column>
                </el-table>
                <pagination v-show="userTotal > 0" :total="userTotal" :page.sync="groupQueryParams.pageNum"
                            :limit.sync="groupQueryParams.pageSize"
                            @pagination="listUserByGroup"
                />
              </el-col>
            </template>
          </el-row>

        </el-form>
      </template>
      <div slot="footer" class="dialog-footer" v-if="!readonly">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog title="添加用户" :visible.sync="openUserDialog" width="1000px" append-to-body
               :close-on-click-modal="false"
    >
      <el-table :ref="(el)=>$refs['userTable'] = el" :data="userTable" :max-height="600"
                @selection-change="handleUserTableSelection"
      >
        <el-table-column type="selection" width="50" align="center"/>
        <el-table-column label="用户名" prop="userName"/>
        <el-table-column label="昵称" prop="nickName"/>
        <el-table-column label="手机" prop="phonenumber"/>
        <el-table-column label="邮箱" prop="email"/>
        <el-table-column label="部门" prop="dept.deptName"/>
        <el-table-column label="状态">
          <template slot="header" slot-scope="scope">
            <el-input
              v-model="search"
              size="mini"
              placeholder="输入用户名或昵称搜索"
            />
          </template>
          <template slot-scope="scope">
            <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
          </template>
        </el-table-column>
      </el-table>

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="addUserToGroup">确 定</el-button>
        <el-button @click="cancelUserTable">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  createUserGroup,
  updateUserGroup,
  deleteUserGroup,
  getUserGroup,
  listUserGroup,
  changeUserGroupStatus, listUserByGroup, deleteUserInGroup
} from '@/api/system/userGroup'
import { SystemStatusEnum } from '@/utils/constants'
import { listUser } from '@/api/system/user'

export default {
  name: 'UserGroup',
  dicts: ['sys_normal_disable'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      userTotal: 0,
      // 用户组列表
      list: [],
      // 用户列表
      users: [],
      userTable: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      readonly: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 20,
        name: null,
        status: null,
        createTime: []
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [{ required: true, message: '组名不能为空', trigger: 'blur' }],
        description: [{ required: true, message: '描述不能为空', trigger: 'blur' }],
        memberUserIds: [{ required: true, message: '成员不能为空', trigger: 'change' }],
        status: [{ required: true, message: '状态不能为空', trigger: 'blur' }]
      },
      groupQueryParams: {
        pageNum: 1,
        pageSize: 10,
        groupId: 0
      },
      openUserDialog: false,
      search: '',
      userTableForm: {}
    }
  },
  watch: {
    search(newVal) {
      if (newVal != '') {
        this.userTable = this.users.filter(item => {
          if (item.nickName.indexOf(newVal) > -1 || item.userName.indexOf(newVal) > -1) {
            return true
          } else if (item.dept != null && item.dept.deptName.indexOf(newVal) > -1) {
            return true
          }
          return false
        })
      }
    }
  },
  created() {
    this.getList()
    // 获得用户列表
    listUser().then(response => {
      this.users = response.rows
      this.userTable = this.users
    })
  },
  methods: {
    /** 查询列表 */
    getList() {
      this.loading = true
      // 执行查询
      listUserGroup(this.queryParams).then(response => {
        this.list = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    /** 取消按钮 */
    cancel() {
      this.open = false
      this.reset()
    },
    /** 表单重置 */
    reset() {
      this.form = {
        id: undefined,
        name: undefined,
        description: undefined,
        memberUserIds: [],
        status: SystemStatusEnum.ENABLE
      }
      this.resetGroup()
      this.readonly = false
      this.resetForm('form')
    },
    resetGroup() {
      this.groupQueryParams = {
        pageNum: 1,
        pageSize: 10,
        groupId: 0
      }
      this.userTableForm = {
        memberUserIds: [],
        id: undefined
      }
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
      this.title = '添加用户组'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id
      getUserGroup(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = '修改用户组'
        this.getUserListInGroup()
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (!valid) {
          return
        }
        // 修改的提交
        if (this.form.id != null) {
          this.form.memberUserIds = undefined // 卸载用户组成员信息，已经在其他表格中同步完成
          updateUserGroup(this.form).then(response => {
            this.$modal.msgSuccess('修改成功')
            this.open = false
            this.getList()
          })
          return
        }
        // 添加的提交
        createUserGroup(this.form).then(response => {
          this.$modal.msgSuccess('新增成功')
          this.open = false
          this.getList()
        })
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const id = row.id
      this.$modal.confirm('是否确认删除用户组编号为"' + id + '"的数据项?').then(function() {
        return deleteUserGroup(id)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    handleInfo(row) {
      this.reset()
      this.readonly = true
      const id = row.id
      getUserGroup(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = '查看用户组'
        this.getUserListInGroup()
      })
    },
    getUserListInGroup() {
      this.resetGroup()
      this.groupQueryParams.groupId = this.form.id
      this.listUserByGroup()
    },
    listUserByGroup() {
      listUserByGroup(this.groupQueryParams).then(response => {
        this.form.users = response.rows
        this.userTotal = response.total
      })
    },
    handleUserDeleteInGroup(row) {
      const userId = row.userId
      deleteUserInGroup({ userId, groupId: this.form.id }).then(response => {
        this.form.memberUserIds = this.form.memberUserIds.filter(item => item != userId)
        this.listUserByGroup()
      })
    },
    handleStatusChange(row) {
      let text = row.status === '0' ? '启用' : '停用'
      this.$modal.confirm('确认要"' + text + '""' + row.name + '"用户组吗？').then(function() {
        return changeUserGroupStatus(row.userId, row.status)
      }).then(() => {
        this.$modal.msgSuccess(text + '成功')
      }).catch(function() {
        row.status = row.status === '0' ? '1' : '0'
      })
    },
    handleAddUserInGroup() {
      const _this = this
      this.openUserDialog = true
      this.$nextTick(() => {
        _this.$refs.userTable.clearSelection()
        this.userTable.forEach((user) => {
          if (_this.form.memberUserIds.filter(item => item == user.userId).length > 0) {
            _this.$refs.userTable.toggleRowSelection(user, true)
          }
        })
      })

    },
    cancelUserTable() {
      this.openUserDialog = false
    },
    addUserToGroup() {
      this.userTableForm.id = this.form.id
      updateUserGroup(this.userTableForm).then(response => {
        this.$modal.msgSuccess('用户组成员更新成功')
        this.openUserDialog = false
        this.form.memberUserIds = this.userTableForm.memberUserIds
        this.getUserListInGroup()
      })
    },
    handleUserTableSelection(selection) {
      this.userTableForm.memberUserIds = selection.map(item => item.userId)
    }
  }
}
</script>
