package com.alphay.boot.bpm.model.vo;

import lombok.Data;

/**
 * 流程任务分配规则
 *
 * @author Nottyjay
 */
@Data
public class BpmTaskAssignRuleResponseVo extends BpmTaskAssignRuleBaseVo {

  private Long id;

  private String modelId;

  private String processDefinitionId;

  private String taskDefinitionKey;

  private String taskDefinitionName;
}
