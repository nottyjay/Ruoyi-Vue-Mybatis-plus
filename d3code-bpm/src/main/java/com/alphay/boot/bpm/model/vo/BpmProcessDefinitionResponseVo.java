package com.alphay.boot.bpm.model.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/** */
@Data
public class BpmProcessDefinitionResponseVo {

  private String id;

  private Integer version;

  @NotEmpty(message = "流程名称不能为空")
  private String name;

  private String description;

  @NotEmpty(message = "流程分类不能为空")
  private String category;

  private Integer formType;
  private Long formId;
  private String formConf;
  private List<String> formFields;
  private String formCustomCreatePath;
  private String formCustomViewPath;

  private Integer suspensionState;
}
