package com.alphay.boot.bpm.model.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class BpmTaskCheckRequestVo {

  @NotEmpty(message = "任务编号不能为空")
  private String id;

  @NotEmpty(message = "审批意见不能为空")
  private String reason;
}
