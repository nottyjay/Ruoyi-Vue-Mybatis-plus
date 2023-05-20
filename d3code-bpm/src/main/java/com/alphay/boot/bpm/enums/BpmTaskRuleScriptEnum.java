package com.alphay.boot.bpm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * BPM 任务规则的脚本枚举
 *
 * @author Nottyjay
 */
@Getter
@AllArgsConstructor
public enum BpmTaskRuleScriptEnum {
  START_USER(10L, "流程发起人"),

  LEADER_X1(20L, "流程发起人的一级领导"),
  LEADER_X2(21L, "流程发起人的二级领导");

  /** 脚本编号 */
  private final Long id;
  /** 脚本描述 */
  private final String desc;
}
