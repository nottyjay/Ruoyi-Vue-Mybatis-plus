package com.alphay.boot.bpm.model.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class BpmTaskUpdateAssigneeRequestVo {
  @NotEmpty(message = "任务编号不能为空")
  private String id;

  @NotNull(message = "新审批人的用户编号不能为空")
  private Long assigneeUserId;
}
