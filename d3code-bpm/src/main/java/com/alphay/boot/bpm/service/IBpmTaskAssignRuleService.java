package com.alphay.boot.bpm.service;

import com.alphay.boot.bpm.domain.BpmTaskAssignRule;
import com.alphay.boot.bpm.model.vo.BpmTaksAssignRuleCreateRequestVo;
import com.alphay.boot.bpm.model.vo.BpmTaskAssignRuleResponseVo;
import com.alphay.boot.bpm.model.vo.BpmTaskAssignRuleUpdateRequestVo;
import org.flowable.engine.delegate.DelegateExecution;

import java.util.List;
import java.util.Set;

/**
 * 流程模型分配规则接口
 *
 * @author Nottyjay
 */
public interface IBpmTaskAssignRuleService {

  /**
   * 校验流程模型的任务分配规则全部都配置了 目的：如果有规则未配置，会导致流程任务找不到负责人，进而流程无法进行下去！
   *
   * @param id
   */
  void checkTaskAssignRuleAllConfig(String id);

  /**
   * 判断指定流程模型和流程定义的分配规则是否相等
   *
   * @param modelId
   * @param processDefinitionId
   * @return
   */
  boolean isTaskAssignRulesEquals(String modelId, String processDefinitionId);

  /**
   * 将流程流程模型的任务分配规则，复制一份给流程定义
   *
   * @param fromModelId
   * @param processDefinitionId
   */
  void copyTaskAssignRules(String fromModelId, String processDefinitionId);

  /**
   * 获得流程定义的任务分配规则数组
   *
   * @param processDefinitionId
   * @param taskDefinitionKey
   * @return
   */
  List<BpmTaskAssignRule> getTaskAssignRuleListByProcessDefinitionId(
      String processDefinitionId, String taskDefinitionKey);

  /**
   * 通过模型ID查找流程规则设置
   *
   * @param modelId
   * @return
   */
  List<BpmTaskAssignRule> getTaskAssignRuleListByModelId(String modelId);

  /**
   * 获得流程定义的任务分配规则数组
   *
   * @param modelId
   * @param processDefinitionId
   * @return
   */
  List<BpmTaskAssignRuleResponseVo> getTaskAssignRuleList(
      String modelId, String processDefinitionId);

  /**
   * 创建任务分配规则
   *
   * @param requestVo
   * @return
   */
  Long createTaskAssignRule(BpmTaksAssignRuleCreateRequestVo requestVo);

  /**
   * 更新任务分配规则
   *
   * @param requestVo
   */
  void updateTaskAssignRule(BpmTaskAssignRuleUpdateRequestVo requestVo);

  /**
   * 计算当前执行任务的处理人
   *
   * @param execution
   * @return
   */
  Set<Long> calculateTaskCandidateUsers(DelegateExecution execution);
}
