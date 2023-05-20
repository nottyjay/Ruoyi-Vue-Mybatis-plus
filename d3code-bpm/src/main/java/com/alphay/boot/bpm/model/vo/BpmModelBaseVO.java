package com.alphay.boot.bpm.model.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/** BPM 流程模型 Base Vo */
@Data
public class BpmModelBaseVO {

  @NotEmpty(message = "流程标识不能为空")
  private String key;

  @NotEmpty(message = "流程名称不能为空")
  private String name;

  private String description;

  @NotEmpty(message = "流程分类不能为空")
  private String category;

  private Integer formType;
  private Long formId;

  private String formCustomCreatePath;

  private String formCustomViewPath;
}
