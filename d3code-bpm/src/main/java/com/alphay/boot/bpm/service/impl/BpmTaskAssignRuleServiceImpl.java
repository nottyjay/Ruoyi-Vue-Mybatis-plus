package com.alphay.boot.bpm.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alphay.boot.bpm.DictTypeConstants;
import com.alphay.boot.bpm.convert.BpmTaskAssignRuleConvert;
import com.alphay.boot.bpm.domain.BpmTaskAssignRule;
import com.alphay.boot.bpm.domain.BpmUserGroup;
import com.alphay.boot.bpm.enums.BpmTaskAssignRuleTypeEnum;
import com.alphay.boot.bpm.framework.core.script.BpmTaskAssignScript;
import com.alphay.boot.bpm.mapper.BpmTaskAssignRuleMapper;
import com.alphay.boot.bpm.service.IBpmModelService;
import com.alphay.boot.bpm.service.IBpmProcessDefinitionService;
import com.alphay.boot.bpm.service.IBpmTaskAssignRuleService;
import com.alphay.boot.bpm.service.IBpmUserGroupService;
import com.alphay.boot.bpm.utils.FlowableUtil;
import com.alphay.boot.common.core.domain.entity.SysDept;
import com.alphay.boot.common.core.domain.entity.SysUser;
import com.alphay.boot.common.enums.SystemStatusEnum;
import com.alphay.boot.common.exception.ServiceException;
import com.alphay.boot.common.utils.JsonUtil;
import com.alphay.boot.common.utils.ObjectUtil;
import com.alphay.boot.common.utils.collection.CollectionUtil;
import com.alphay.boot.system.api.*;
import com.alphay.boot.bpm.model.vo.BpmTaksAssignRuleCreateRequestVo;
import com.alphay.boot.bpm.model.vo.BpmTaskAssignRuleResponseVo;
import com.alphay.boot.bpm.model.vo.BpmTaskAssignRuleUpdateRequestVo;
import com.alphay.boot.system.api.*;
import lombok.extern.slf4j.Slf4j;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.UserTask;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.engine.delegate.DelegateExecution;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.*;

import static cn.hutool.core.text.CharSequenceUtil.format;

/**
 * 流程模型分配规则接口实现
 *
 * @author Nottyjay
 */
@Service
@Validated
@Slf4j
public class BpmTaskAssignRuleServiceImpl implements IBpmTaskAssignRuleService {

  @Resource private BpmTaskAssignRuleMapper taskAssignRuleMapper;
  @Resource @Lazy private IBpmModelService modelService;
  @Resource private IBpmUserGroupService userGroupService;
  @Resource private RoleApi roleApi;
  @Resource private DeptApi deptApi;
  @Resource private PostApi postApi;
  @Resource private AdminApi adminApi;
  @Resource private DictDataApi dictDataApi;

  @Resource @Lazy // 解决循环依赖
  private IBpmProcessDefinitionService processDefinitionService;

  /** 任务分配脚本 */
  private Map<Long, BpmTaskAssignScript> scriptMap = Collections.emptyMap();

  @Override
  public List<BpmTaskAssignRuleResponseVo> getTaskAssignRuleList(
      String modelId, String processDefinitionId) {
    // 获得规则
    List<BpmTaskAssignRule> rules = Collections.emptyList();
    BpmnModel model = null;
    if (StrUtil.isNotEmpty(modelId)) {
      rules = getTaskAssignRuleListByModelId(modelId);
      model = modelService.getBpmnModel(modelId);
    } else if (StrUtil.isNotEmpty(processDefinitionId)) {
      rules = getTaskAssignRuleListByProcessDefinitionId(processDefinitionId, null);
      model = processDefinitionService.getBpmnModel(processDefinitionId);
    }
    if (model == null) {
      return Collections.emptyList();
    }
    // 获得用户任务，只有用户任务才可以设置分配规则
    List<UserTask> userTasks = FlowableUtil.getBpmnModelElements(model, UserTask.class);
    if (CollUtil.isEmpty(userTasks)) {
      return Collections.emptyList();
    }
    // 转换数据
    return BpmTaskAssignRuleConvert.INSTANCE.convertList(userTasks, rules);
  }

  @Override
  public List<BpmTaskAssignRule> getTaskAssignRuleListByProcessDefinitionId(
      String processDefinitionId, String taskDefinitionKey) {
    return taskAssignRuleMapper.selectListByProcessDefinitionId(
        processDefinitionId, taskDefinitionKey);
  }

  @Override
  public Long createTaskAssignRule(BpmTaksAssignRuleCreateRequestVo requestVo) {
    // 校验参数
    validTaskAssignRuleOptions(requestVo.getType(), requestVo.getOptions());
    // 校验是否已经配置
    BpmTaskAssignRule existRule =
        taskAssignRuleMapper.selectListByModelIdAndTaskDefinitionKey(
            requestVo.getModelId(), requestVo.getTaskDefinitionKey());
    if (existRule != null) {
      throw new ServiceException(
          "流程("
              + requestVo.getModelId()
              + ") 的任务("
              + requestVo.getTaskDefinitionKey()
              + ") 已经存在分配规则");
    }

    // 存储
    BpmTaskAssignRule rule =
        BpmTaskAssignRuleConvert.INSTANCE
            .convert(requestVo)
            .setProcessDefinitionId(BpmTaskAssignRule.PROCESS_DEFINITION_ID_NULL); // 只有流程模型，才允许新建
    taskAssignRuleMapper.insert(rule);
    return rule.getId();
  }

  @Override
  public void updateTaskAssignRule(BpmTaskAssignRuleUpdateRequestVo requestVo) {
    // 校验参数
    validTaskAssignRuleOptions(requestVo.getType(), requestVo.getOptions());
    // 校验是否存在
    BpmTaskAssignRule existRule = taskAssignRuleMapper.selectById(requestVo.getId());
    if (existRule == null) {
      throw new ServiceException("流程任务分配规则不存在");
    }
    // 只允许修改流程模型的规则
    if (!Objects.equals(
        BpmTaskAssignRule.PROCESS_DEFINITION_ID_NULL, existRule.getProcessDefinitionId())) {
      throw new ServiceException("只有流程模型的任务分配规则，才允许被修改");
    }

    // 执行更新
    taskAssignRuleMapper.updateById(BpmTaskAssignRuleConvert.INSTANCE.convert(requestVo));
  }

  @Override
  public void checkTaskAssignRuleAllConfig(String id) {
    // 一个用户任务都没配置，所以无需配置规则
    List<BpmTaskAssignRuleResponseVo> taskAssignRules = getTaskAssignRuleList(id, null);
    if (CollUtil.isEmpty(taskAssignRules)) {
      return;
    }
    // 校验未配置规则的任务
    taskAssignRules.forEach(
        rule -> {
          if (CollUtil.isEmpty(rule.getOptions())) {
            throw new ServiceException(
                "部署流程失败，原因：用户任务(" + rule.getTaskDefinitionName() + ")未配置分配规则，请点击【修改流程】按钮进行配置");
          }
        });
  }

  @Override
  public List<BpmTaskAssignRule> getTaskAssignRuleListByModelId(String modelId) {
    return taskAssignRuleMapper.selectListByModelId(modelId);
  }

  @Override
  public boolean isTaskAssignRulesEquals(String modelId, String processDefinitionId) {
    // 调用 VO 接口的原因是，过滤掉流程模型不需要的规则，保持和 copyTaskAssignRules 方法的一致性
    List<BpmTaskAssignRuleResponseVo> modelRules = getTaskAssignRuleList(modelId, null);
    List<BpmTaskAssignRuleResponseVo> processInstanceRules =
        getTaskAssignRuleList(null, processDefinitionId);
    if (modelRules.size() != processInstanceRules.size()) {
      return false;
    }

    // 遍历，匹配对应的规则
    Map<String, BpmTaskAssignRuleResponseVo> processInstanceRuleMap =
        CollectionUtil.convertMap(
            processInstanceRules, BpmTaskAssignRuleResponseVo::getTaskDefinitionKey);
    for (BpmTaskAssignRuleResponseVo modelRule : modelRules) {
      BpmTaskAssignRuleResponseVo processInstanceRule =
          processInstanceRuleMap.get(modelRule.getTaskDefinitionKey());
      if (processInstanceRule == null) {
        return false;
      }
      if (!ObjectUtil.equals(modelRule.getType(), processInstanceRule.getType())
          || !ObjectUtil.equals(modelRule.getOptions(), processInstanceRule.getOptions())) {
        return false;
      }
    }
    return true;
  }

  @Override
  public void copyTaskAssignRules(String fromModelId, String processDefinitionId) {
    List<BpmTaskAssignRuleResponseVo> rules = getTaskAssignRuleList(fromModelId, null);
    if (CollUtil.isEmpty(rules)) {
      return;
    }
    // 开始复制
    List<BpmTaskAssignRule> newRules = BpmTaskAssignRuleConvert.INSTANCE.convertList2(rules);
    newRules.forEach(
        rule ->
            rule.setProcessDefinitionId(processDefinitionId)
                .setId(null)
                .setCreateTime(null)
                .setUpdateTime(null));
    taskAssignRuleMapper.insertBatch(newRules);
  }

  @Override
  public Set<Long> calculateTaskCandidateUsers(DelegateExecution execution) {
    BpmTaskAssignRule rule = getTaskRule(execution);
    return calculateTaskCandidateUsers(execution, rule);
  }

  BpmTaskAssignRule getTaskRule(DelegateExecution execution) {
    List<BpmTaskAssignRule> taskRules =
        getTaskAssignRuleListByProcessDefinitionId(
            execution.getProcessDefinitionId(), execution.getCurrentActivityId());
    if (CollUtil.isEmpty(taskRules)) {
      throw new FlowableException(
          format(
              "流程任务({}/{}/{}) 找不到符合的任务规则",
              execution.getId(),
              execution.getProcessDefinitionId(),
              execution.getCurrentActivityId()));
    }
    if (taskRules.size() > 1) {
      throw new FlowableException(
          format(
              "流程任务({}/{}/{}) 找到过多任务规则({})",
              execution.getId(),
              execution.getProcessDefinitionId(),
              execution.getCurrentActivityId()));
    }
    return taskRules.get(0);
  }

  private void validTaskAssignRuleOptions(Integer type, Set<Long> options) {
    if (Objects.equals(type, BpmTaskAssignRuleTypeEnum.ROLE.getType())) {
      roleApi.validRoleList(options);
    } else if (ObjectUtil.equalsAny(
        type,
        BpmTaskAssignRuleTypeEnum.DEPT_MEMBER.getType(),
        BpmTaskAssignRuleTypeEnum.DEPT_LEADER.getType())) {
      deptApi.validateDeptList(options);
    } else if (Objects.equals(type, BpmTaskAssignRuleTypeEnum.POST.getType())) {
      postApi.validPostList(options);
    } else if (Objects.equals(type, BpmTaskAssignRuleTypeEnum.USER.getType())) {
      adminApi.validateUserList(options);
    } else if (Objects.equals(type, BpmTaskAssignRuleTypeEnum.USER_GROUP.getType())) {
      userGroupService.validUserGroups(options);
    } else if (Objects.equals(type, BpmTaskAssignRuleTypeEnum.SCRIPT.getType())) {
      dictDataApi.validateDictDataList(
          DictTypeConstants.TASK_ASSIGN_SCRIPT,
          CollectionUtil.convertSet(options, String::valueOf));
    } else {
      throw new IllegalArgumentException(format("未知的规则类型({})", type));
    }
  }

  private Set<Long> calculateTaskCandidateUsersByRole(BpmTaskAssignRule rule) {
    return roleApi.getUserRoleIdListByRoleIds(rule.getOptions());
  }

  private Set<Long> calculateTaskCandidateUsersByDeptMember(BpmTaskAssignRule rule) {
    List<SysUser> users = adminApi.getUserListByDeptIds(rule.getOptions());
    return CollectionUtil.convertSet(users, SysUser::getUserId);
  }

  private Set<Long> calculateTaskCandidateUsersByDeptLeader(BpmTaskAssignRule rule) {
    List<SysDept> depts = deptApi.getDeptList(rule.getOptions());
    return CollectionUtil.convertSet(depts, SysDept::getLeaderUserId);
  }

  private Set<Long> calculateTaskCandidateUsersByPost(BpmTaskAssignRule rule) {
    List<SysUser> users = adminApi.getUsersByPostIds(rule.getOptions());
    return CollectionUtil.convertSet(users, SysUser::getUserId);
  }

  private Set<Long> calculateTaskCandidateUsersByUser(BpmTaskAssignRule rule) {
    return rule.getOptions();
  }

  private Set<Long> calculateTaskCandidateUsersByUserGroup(BpmTaskAssignRule rule) {
    List<BpmUserGroup> userGroups = userGroupService.getUserGroupList(rule.getOptions());
    Set<Long> userIds = new HashSet<>();
    userGroups.forEach(group -> userIds.addAll(group.getMemberUserIds()));
    return userIds;
  }

  private Set<Long> calculateTaskCandidateUsersByScript(
      DelegateExecution execution, BpmTaskAssignRule rule) {
    // 获得对应的脚本
    List<BpmTaskAssignScript> scripts = new ArrayList<>(rule.getOptions().size());
    rule.getOptions()
        .forEach(
            id -> {
              BpmTaskAssignScript script = scriptMap.get(id);
              if (script == null) {
                throw new ServiceException("操作失败，原因：任务分配脚本(" + id + ") 不存在");
              }
              scripts.add(script);
            });
    // 逐个计算任务
    Set<Long> userIds = new HashSet<>();
    scripts.forEach(
        script -> CollUtil.addAll(userIds, script.calculateTaskCandidateUsers(execution)));
    return userIds;
  }

  Set<Long> calculateTaskCandidateUsers(DelegateExecution execution, BpmTaskAssignRule rule) {
    Set<Long> assigneeUserIds = null;
    if (Objects.equals(BpmTaskAssignRuleTypeEnum.ROLE.getType(), rule.getType())) {
      assigneeUserIds = calculateTaskCandidateUsersByRole(rule);
    } else if (Objects.equals(BpmTaskAssignRuleTypeEnum.DEPT_MEMBER.getType(), rule.getType())) {
      assigneeUserIds = calculateTaskCandidateUsersByDeptMember(rule);
    } else if (Objects.equals(BpmTaskAssignRuleTypeEnum.DEPT_LEADER.getType(), rule.getType())) {
      assigneeUserIds = calculateTaskCandidateUsersByDeptLeader(rule);
    } else if (Objects.equals(BpmTaskAssignRuleTypeEnum.POST.getType(), rule.getType())) {
      assigneeUserIds = calculateTaskCandidateUsersByPost(rule);
    } else if (Objects.equals(BpmTaskAssignRuleTypeEnum.USER.getType(), rule.getType())) {
      assigneeUserIds = calculateTaskCandidateUsersByUser(rule);
    } else if (Objects.equals(BpmTaskAssignRuleTypeEnum.USER_GROUP.getType(), rule.getType())) {
      assigneeUserIds = calculateTaskCandidateUsersByUserGroup(rule);
    } else if (Objects.equals(BpmTaskAssignRuleTypeEnum.SCRIPT.getType(), rule.getType())) {
      assigneeUserIds = calculateTaskCandidateUsersByScript(execution, rule);
    }

    // 移除被禁用的用户
    removeDisableUsers(assigneeUserIds);
    // 如果候选人为空，抛出异常
    if (CollUtil.isEmpty(assigneeUserIds)) {
      log.error(
          "[calculateTaskCandidateUsers][流程任务({}/{}/{}) 任务规则({}) 找不到候选人]",
          execution.getId(),
          execution.getProcessDefinitionId(),
          execution.getCurrentActivityId(),
          JsonUtil.toJsonHex(rule));
      throw new ServiceException("操作失败，原因：找不到任务的审批人！");
    }
    return assigneeUserIds;
  }

  void removeDisableUsers(Set<Long> assigneeUserIds) {
    if (CollUtil.isEmpty(assigneeUserIds)) {
      return;
    }
    Map<Long, SysUser> userMap = adminApi.getUserMap(assigneeUserIds);
    assigneeUserIds.removeIf(
        id -> {
          SysUser user = userMap.get(id);
          return user == null || !SystemStatusEnum.ENABLE.getStatus().equals(user.getStatus());
        });
  }
}
