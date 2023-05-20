package com.alphay.boot.bpm.model.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class BpmTaskAssignRuleUpdateRequestVo extends BpmTaskAssignRuleBaseVo {

  @NotNull(message = "任务分配规则的编号不能为空")
  private Long id;
}
