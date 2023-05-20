package com.alphay.boot.bpm.model.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class BpmProcessInstanceCancelRequestVo {

  @NotEmpty(message = "流程实例的编号不能为空")
  private String id;

  @NotEmpty(message = "取消原因不能为空")
  private String reason;
}
