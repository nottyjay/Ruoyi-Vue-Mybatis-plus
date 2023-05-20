package com.alphay.boot.bpm.model.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 修改流程模型Vo
 *
 * @author Nottyjay
 */
@Data
public class BpmModelUpdateRequestVo {

  @NotEmpty(message = "编号不能为空")
  private String id;

  private String name;

  private String description;

  private String category;

  private String bpmnXml;

  private Integer formType;
  private Long formId;
  private String formCustomCreatePath;
  private String formCustomViewPath;
}
