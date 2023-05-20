package com.d3code.bpm.mapper;

import com.d3code.bpm.domain.BpmTaskAssignRule;
import com.d3code.common.mybatis.mapper.BaseMapperX;
import com.d3code.common.mybatis.query.LambdaQueryWrapperX;

import java.util.List;

public interface BpmTaskAssignRuleMapper extends BaseMapperX<BpmTaskAssignRule> {
  default List<BpmTaskAssignRule> selectListByModelId(String modelId) {
    return selectList(
        new LambdaQueryWrapperX<BpmTaskAssignRule>()
            .eq(BpmTaskAssignRule::getModelId, modelId)
            .eq(
                BpmTaskAssignRule::getProcessDefinitionId,
                BpmTaskAssignRule.PROCESS_DEFINITION_ID_NULL));
  }

  default List<BpmTaskAssignRule> selectListByProcessDefinitionId(
      String processDefinitionId, String taskDefinitionKey) {
    return selectList(
        new LambdaQueryWrapperX<BpmTaskAssignRule>()
            .eq(BpmTaskAssignRule::getProcessDefinitionId, processDefinitionId)
            .eqIfPresent(BpmTaskAssignRule::getTaskDefinitionKey, taskDefinitionKey));
  }

  default BpmTaskAssignRule selectListByModelIdAndTaskDefinitionKey(
      String modelId, String taskDefinitionKey) {
    return selectOne(
        new LambdaQueryWrapperX<BpmTaskAssignRule>()
            .eq(BpmTaskAssignRule::getModelId, modelId)
            .eq(
                BpmTaskAssignRule::getProcessDefinitionId,
                BpmTaskAssignRule.PROCESS_DEFINITION_ID_NULL)
            .eq(BpmTaskAssignRule::getTaskDefinitionKey, taskDefinitionKey));
  }
}
