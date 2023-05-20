package com.alphay.boot.bpm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 流程实例的状态
 *
 * @author Nottyjay
 */
@Getter
@AllArgsConstructor
public enum BpmProcessInstanceStatusEnum {
  RUNNING(1, "进行中"),
  FINISH(2, "已完成");

  /** 状态 */
  private final Integer status;
  /** 描述 */
  private final String desc;
}
