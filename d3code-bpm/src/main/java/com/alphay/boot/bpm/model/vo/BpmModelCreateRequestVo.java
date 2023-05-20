package com.alphay.boot.bpm.model.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 创建流程模块Vo
 *
 * @author Nottyjay
 */
@Data
public class BpmModelCreateRequestVo {

  @NotEmpty(message = "流程标识不能为空")
  private String key;

  @NotEmpty(message = "流程名称不能为空")
  private String name;

  private String description;
}
