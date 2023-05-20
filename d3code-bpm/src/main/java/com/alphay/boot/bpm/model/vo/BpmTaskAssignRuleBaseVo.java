package com.alphay.boot.bpm.model.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * 流程任务分配规则 Base VO，提供给添加、修改、详细的子 VO 使用
 *
 * @author Nottyjay
 */
@Data
public class BpmTaskAssignRuleBaseVo {

  @NotNull(message = "规则类型不能为空")
  private Integer type;

  @NotNull(message = "规则值数组不能为空")
  private Set<Long> options;
}
