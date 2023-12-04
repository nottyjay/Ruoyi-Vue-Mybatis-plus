package com.alphay.boot.bpm.api.model.dto;

import lombok.Data;

/**
 * BPM模型 MetaInfo
 *
 * @author Nottyjay
 */
@Data
public class BpmModelMetaInfoRespDTO {

  /** 流程描述 */
  private String description;

  /** 表单类型 */
  private Integer formType;

  /** 表单编号 在表单类型为 {@link BpmModelFormTypeEnum#NORMAL} 时 */
  private Long formId;

  /** 自定义表单的提交路径，使用 Vue 的路由地址 在表单类型为 {@link BpmModelFormTypeEnum#CUSTOM} 时 */
  private String formCustomCreatePath;

  /** 自定义表单的查看路径，使用 Vue 的路由地址 在表单类型为 {@link BpmModelFormTypeEnum#CUSTOM} 时 */
  private String formCustomViewPath;
}
