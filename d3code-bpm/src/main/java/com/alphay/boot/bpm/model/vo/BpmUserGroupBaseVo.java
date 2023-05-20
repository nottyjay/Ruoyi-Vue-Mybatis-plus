package com.alphay.boot.bpm.model.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * 用户组 Base VO，提供给添加、修改、详细的子 VO 使用
 *
 * @author Nottyjay
 */
@Data
public class BpmUserGroupBaseVo {

  @NotNull(message = "组名不能为空")
  private String name;

  @NotNull(message = "描述不能为空")
  private String description;

  @NotNull(message = "成员编号数组不能为空")
  private Set<Long> memberUserIds;

  @NotNull(message = "状态不能为空")
  private Integer status;
}
