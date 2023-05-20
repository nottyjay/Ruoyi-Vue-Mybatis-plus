package com.alphay.boot.bpm.model.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * BPM 发送流程实例不通过时 Request DTO
 *
 * @author Nottyjay
 */
@Data
public class BpmMessageSendWhenProcessInstanceCheckRequestDTO {
  /** 流程实例的编号 */
  @NotEmpty(message = "流程实例的编号不能为空")
  private String processInstanceId;
  /** 流程实例的名字 */
  @NotEmpty(message = "流程实例的名字不能为空")
  private String processInstanceName;

  @NotNull(message = "发起人的用户编号")
  private Long startUserId;

  /** 不通过理由 */
  private String reason;
}
