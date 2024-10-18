<template>
  <div class="app-container">
    <!-- 列表 -->
    <el-table v-loading="loading" :data="list">
      <el-table-column label="定义编号" align="center" prop="id" width="400"/>
      <el-table-column label="定义名称" align="center" prop="name" width="100">
        <template v-slot="scope">
          <el-button type="text" @click="handleBpmnDetail(scope.row)">
            <span>{{ scope.row.name }}</span>
          </el-button>
        </template>
      </el-table-column>
      <el-table-column label="定义分类" align="center" prop="category" width="100">
        <template v-slot="scope">
          <dict-tag :type="dict.type.bpm_model_category" :value="scope.row.category"/>
        </template>
      </el-table-column>
      <el-table-column label="表单信息" align="center" prop="formType" width="200">
        <template v-slot="scope">
          <el-button v-if="scope.row.formId" type="text" @click="handleFormDetail(scope.row)">
            <span>{{ scope.row.formName }}</span>
          </el-button>
          <el-button v-else-if="scope.row.formCustomCreatePath" type="text" @click="handleFormDetail(scope.row)">
            <span>{{ scope.row.formCustomCreatePath }}</span>
          </el-button>
          <label v-else>暂无表单</label>
        </template>
      </el-table-column>
      <el-table-column label="流程版本" align="center" prop="processDefinition.version" width="80">
        <template v-slot="scope">
          <el-tag size="medium" v-if="scope.row">v{{ scope.row.version }}</el-tag>
          <el-tag size="medium" type="warning" v-else>未部署</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="version" width="80">
        <template v-slot="scope">
          <el-tag type="success" v-if="scope.row.suspensionState === 1">激活</el-tag>
          <el-tag type="warning" v-if="scope.row.suspensionState === 2">挂起</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="部署时间" align="center" prop="deploymentTime" width="180">
        <template v-slot="scope">
          <span>{{ parseTime(scope.row.deploymentTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="定义描述" align="center" prop="description" width="300" show-overflow-tooltip/>
      <el-table-column label="操作" align="center" width="150" fixed="right">
        <template v-slot="scope">
          <el-button size="mini" type="text" icon="el-icon-s-custom" @click="handleAssignRule(scope.row)"
                     v-hasPermi="['bpm:task-assign-rule:update']"
          >分配规则
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 流程表单配置详情 -->
    <el-dialog title="表单详情" :visible.sync="detailOpen" width="50%" append-to-body>
      <parser :key="new Date().getTime()" :config="detailForm" :need-submit="false"/>
    </el-dialog>

    <!-- 流程模型图的预览 -->
    <el-dialog title="流程图" :visible.sync="showBpmnOpen" width="80%" append-to-body>
      <my-process-viewer key="designer" v-model="bpmnXML" v-bind="bpmnControlForm"/>
    </el-dialog>

    <!-- 分页组件 -->
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- ========== 流程任务分配规则 ========== -->
    <taskAssignRuleDialog ref="taskAssignRuleDialog"/>
  </div>
</template>

<script>
import { getProcessDefinitionBpmnXML, getProcessDefinitionList } from '@/api/bpm/definition'
import taskAssignRuleDialog from '../taskAssignRule/taskAssignRuleDialog'
import { decodeFields } from '@/utils/formGenerator'

export default {
  name: 'processDefinition',
  // components: {
  //   Parser,
  //   taskAssignRuleDialog
  // },
  dicts: ['bpm_model_category'],
  components: {
    taskAssignRuleDialog
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 总条数
      total: 0,
      // 表格数据
      list: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10
      },

      // 流程表单详情
      detailOpen: false,
      detailForm: {
        list: []
      },

      // BPMN 数据
      showBpmnOpen: false,
      bpmnXML: null,
      bpmnControlForm: {
        prefix: 'flowable'
      }

      // 数据字典
    }
  },
  created() {
    const key = this.$route.query && this.$route.query.key
    if (key) {
      this.queryParams['key'] = key
    }
    this.getList()
  },
  methods: {
    /** 查询流程定义列表 */
    getList() {
      this.loading = true
      getProcessDefinitionList(this.queryParams).then(response => {
          this.list = response.rows
          this.total = response.total
          this.loading = false
        }
      )
    },
    /** 流程表单的详情按钮操作 */
    handleFormDetail(row) {
      // 流程表单
      if (row.formId) {
        // 设置值
        this.detailForm = {
          formTemplate: {
            config: JSON.parse(data.conf),
            list: decodeFields(data.fields)
          }
        }
        // 弹窗打开
        this.detailOpen = true
        // 业务表单
      } else if (row.formCustomCreatePath) {
        this.$router.push({ path: row.formCustomCreatePath })
      }
    },
    /** 流程图的详情按钮操作 */
    handleBpmnDetail(row) {
      getProcessDefinitionBpmnXML(row.id).then(response => {
        this.bpmnXML = response.data
        // 弹窗打开
        this.showBpmnOpen = true
      })
    },
    /** 处理任务分配规则列表的按钮操作 */
    handleAssignRule(row) {
      this.$refs['taskAssignRuleDialog'].initProcessDefinition(row.id)
    }
  }
}
</script>

<style lang="scss">
.my-process-designer {
  height: calc(100vh - 200px);
}
</style>
