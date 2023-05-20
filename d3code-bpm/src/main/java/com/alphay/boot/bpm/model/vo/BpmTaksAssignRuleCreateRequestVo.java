package com.alphay.boot.bpm.model.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class BpmTaksAssignRuleCreateRequestVo extends BpmTaskAssignRuleBaseVo {

  @NotEmpty(message = "流程模型的编号不能为空")
  private String modelId;

  @NotEmpty(message = "流程任务定义的编号不能为空")
  private String taskDefinitionKey;
}
