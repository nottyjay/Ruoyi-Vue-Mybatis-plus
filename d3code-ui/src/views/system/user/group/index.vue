<template>
  <div class="app-container">
    <!-- 搜索工作栏 -->
    <el-form :model="queryParams" ref="queryFormRef"  :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="组名" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入组名" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option v-for="dict in sys_normal_disable"
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
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 操作工具栏 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="Plus"  @click="handleAdd"
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
        <template #default="scope">
          <el-switch
            v-model="scope.row.status"
            active-value="0"
            inactive-value="1"
            @change="handleStatusChange(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button type="primary" link icon="Search" @click="handleInfo(scope.row)"
                     v-hasPermi="['system:userGroup:query']"
          >查看
          </el-button>
          <el-button type="primary" link icon="Edit" @click="handleUpdate(scope.row)"
                     v-hasPermi="['system:userGroup:update']"
          >修改
          </el-button>
          <el-button type="primary" link icon="Delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['system:userGroup:delete']"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize"
                @pagination="getList"
    />

    <!-- 对话框(添加 / 修改) -->
    <el-dialog :title="title" v-model="open" width="1000px" append-to-body :close-on-click-modal="false">
      <template v-if="readonly">
        <el-descriptions :column="2" border>
          <el-descriptions-item>
            <template v-slot:label>
              <el-icon><User /></el-icon>
              组名
            </template>
            {{ form.name }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template v-slot:label>
              <el-icon><Iphone /></el-icon>
              状态
            </template>
            <dict-tag :options="sys_normal_disable" :value="form.status"/>
          </el-descriptions-item>
          <el-descriptions-item>
            <template v-slot:label>
              <el-icon><Location /></el-icon>
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
            <template #default="scope">
              <dict-tag :options="sys_normal_disable" :value="scope.row.status"/>
            </template>
          </el-table-column>
        </el-table>
        <pagination v-show="userTotal > 0" :total="userTotal" v-model:page="groupQueryParams.pageNum"
                    v-model:limit="groupQueryParams.pageSize"
                    @pagination="listUserByGroup"
        />
      </template>
      <template v-else>
        <el-form ref="userGroupFormRef" :model="form" :rules="rules" label-width="80px">
          <el-row>
            <el-col :span="12">
              <el-form-item label="组名" prop="name">
                <el-input v-model="form.name" placeholder="请输入组名"/>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="状态" prop="status">
                <el-radio-group v-model="form.status">
                  <el-radio v-for="dict in sys_normal_disable"
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
                    <template #default="scope">
                      <dict-tag :options="sys_normal_disable" :value="scope.row.status"/>
                    </template>
                  </el-table-column>
                  <el-table-column>
                    <template #header="scope">
                      <el-button type="primary" plain icon="Plus"  @click="handleAddUserInGroup"
                                 v-hasPermi="['system:userGroup:add']"
                      >新增
                      </el-button>
                    </template>
                    <template #default="scope">
                      <el-button type="primary" link icon="Delete"
                                 @click="handleUserDeleteInGroup(scope.row)"
                                 v-hasPermi="['system:userGroup:delete']"
                      >删除
                      </el-button>
                    </template>
                  </el-table-column>
                </el-table>
                <pagination v-show="userTotal > 0" :total="userTotal" v-model:page="groupQueryParams.pageNum"
                            v-model:limit="groupQueryParams.pageSize"
                            @pagination="listUserByGroup"
                />
              </el-col>
            </template>
          </el-row>

        </el-form>
      </template>
      <template #footer>
        <div class="dialog-footer" v-if="!readonly">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>

    <el-dialog title="添加用户" v-model="openUserDialog" width="1000px" append-to-body
               :close-on-click-modal="false"
    >
      <el-table ref="userTableRef" :data="userTable" :max-height="600"
                @selection-change="handleUserTableSelection"
      >
        <el-table-column type="selection" width="50" align="center"/>
        <el-table-column label="用户名" prop="userName"/>
        <el-table-column label="昵称" prop="nickName"/>
        <el-table-column label="手机" prop="phonenumber"/>
        <el-table-column label="邮箱" prop="email"/>
        <el-table-column label="部门" prop="dept.deptName"/>
        <el-table-column label="状态">
          <template #header="scope">
            <el-input
              v-model="search"
              placeholder="输入用户名或昵称搜索"
            />
          </template>
          <template #default="scope">
            <dict-tag :options="sys_normal_disable" :value="scope.row.status"/>
          </template>
        </el-table-column>
      </el-table>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="addUserToGroup">确 定</el-button>
          <el-button @click="cancelUserTable">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="UserGroup">
import {
  createUserGroup,
  updateUserGroup,
  deleteUserGroup,
  getUserGroup,
  listUserGroup,
  changeUserGroupStatus, listUserByGroups, deleteUserInGroup
} from '@/api/system/userGroup'
import { SystemStatusEnum } from '@/utils/constants'
import { listUser } from '@/api/system/user'

const { proxy } = getCurrentInstance()
const { sys_normal_disable } = proxy.useDict('sys_normal_disable')

const list = ref([])
const users = ref([])
const userTable = ref([])
const loading = ref(true)
const showSearch = ref(true)
const total = ref(0)
const userTotal = ref(0)
const readonly = ref(false)
const title = ref('')
const open = ref(false)
const openUserDialog = ref(false)
const search = ref('')
const userTableForm = ref({})

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 20,
    name: null,
    status: null,
    createTime: []
  },
  groupQueryParams: {
    pageNum: 1,
    pageSize: 10,
    groupId: 0
  },
  rules: {
    name: [{ required: true, message: '组名不能为空', trigger: 'blur' }],
    description: [{ required: true, message: '描述不能为空', trigger: 'blur' }],
    status: [{ required: true, message: '状态不能为空', trigger: 'blur' }]
  },
})

const { form, queryParams, groupQueryParams, rules } = toRefs(data)

watch(() => search.value, (newVal) => {
  if (newVal !== '') {
    userTable.value = users.value.filter(item => {
      if (item.nickName.includes(newVal) || item.userName.includes(newVal)) {
        return true;
      } else if (item.dept && item.dept.deptName.includes(newVal)) {
        return true;
      }
      return false;
    });
  }
});

/** 查询列表 */
function getList() {
  loading.value = true
  // 执行查询
  listUserGroup(queryParams.value).then(response => {
    list.value = response.rows
    total.value = response.total
    loading.value = false
  })
}

/** 取消按钮 */
function cancel() {
  open.value = false
  reset()
}
/** 表单重置 */
function reset() {
  form.value = {
    id: undefined,
    name: undefined,
    description: undefined,
    memberUserIds: [],
    status: SystemStatusEnum.ENABLE
  }
  resetGroup()
  readonly.value = false
  proxy.resetForm('userGroupFormRef')
}

function resetGroup() {
  groupQueryParams.value = {
    pageNum: 1,
    pageSize: 10,
    groupId: 0
  }
  userTableForm.value = {
    memberUserIds: [],
    id: undefined
  }
}
/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1
  getList()
}
/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm('queryFormRef')
  handleQuery()
}
/** 新增按钮操作 */
function handleAdd() {
  reset()
  open.value = true
  title.value = '添加用户组'
}
/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const id = row.id
  getUserGroup(id).then(response => {
    form.value = response.data
    open.value = true
    title.value = '修改用户组'
    getUserListInGroup()
  })
}
/** 提交按钮 */
function submitForm() {
  proxy.$refs['userGroupFormRef'].validate(valid => {
    if (!valid) {
      return
    }
    // 修改的提交
    if (form.value.id != null) {
      form.value.memberUserIds = undefined // 卸载用户组成员信息，已经在其他表格中同步完成
      updateUserGroup(form.value).then(response => {
        proxy.$modal.msgSuccess('修改成功')
        open.value = false
        getList()
      })
      return
    }
    // 添加的提交
    createUserGroup(form.value).then(response => {
      proxy.$modal.msgSuccess('新增成功')
      open.value = false
      getList()
    })
  })
}
/** 删除按钮操作 */
function handleDelete(row) {
  const id = row.id
  proxy.$modal.confirm('是否确认删除用户组编号为"' + id + '"的数据项?').then(function() {
    return deleteUserGroup(id)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess('删除成功')
  }).catch(() => {
  })
}

function handleInfo(row) {
  reset()
  readonly.value = true
  const id = row.id
  getUserGroup(id).then(response => {
    form.value = response.data
    open.value = true
    title.value = '查看用户组'
    getUserListInGroup()
  })
}

function getUserListInGroup() {
  resetGroup()
  groupQueryParams.value.groupId = form.value.id
  listUserByGroup()
}

function listUserByGroup() {
  listUserByGroups(groupQueryParams.value).then(response => {
    form.value.users = response.rows
    userTotal.value = response.total
  })
}

function handleUserDeleteInGroup(row) {
  const userId = row.userId
  deleteUserInGroup({ userId, groupId: form.value.id }).then(response => {
    form.value.memberUserIds = form.value.memberUserIds.filter(item => item != userId)
    listUserByGroup()
  })
}

function handleStatusChange(row) {
  let text = row.status === '0' ? '启用' : '停用'
  proxy.$modal.confirm('确认要"' + text + '""' + row.name + '"用户组吗？').then(function() {
    return changeUserGroupStatus(row.userId, row.status)
  }).then(() => {
    proxy.$modal.msgSuccess(text + '成功')
  }).catch(function() {
    row.status = row.status === '0' ? '1' : '0'
  })
}

function handleAddUserInGroup() {
  openUserDialog.value = true
  nextTick(() => {
    proxy.$refs.userTableRef.clearSelection()
    userTable.value.forEach((user) => {
      if (form.value.memberUserIds.filter(item => item == user.userId).length > 0) {
        proxy.$refs.userTableRef.toggleRowSelection(user, true)
      }
    })
  })
}

function cancelUserTable() {
  openUserDialog.value = false
}

function addUserToGroup() {
  userTableForm.value.id = form.value.id
  updateUserGroup(userTableForm.value).then(response => {
    proxy.$modal.msgSuccess('用户组成员更新成功')
    openUserDialog.value = false
    form.value.memberUserIds = userTableForm.value.memberUserIds
    getUserListInGroup()
  })
}
function handleUserTableSelection(selection) {
  userTableForm.value.memberUserIds = selection.map(item => item.userId)
}

getList()
// 获得用户列表
listUser().then(response => {
  users.value = response.rows
  userTable.value = users.value
})
</script>
