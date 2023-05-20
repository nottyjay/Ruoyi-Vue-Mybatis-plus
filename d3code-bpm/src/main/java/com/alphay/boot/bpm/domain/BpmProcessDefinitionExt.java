package com.alphay.boot.bpm.domain;

import com.alphay.boot.bpm.enums.BpmModelFormTypeEnum;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.alphay.boot.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(autoResultMap = true)
public class BpmProcessDefinitionExt extends BaseEntity {

  @TableId(type = IdType.AUTO)
  private Long id;

  /** 流程定义编号 */
  private String processDefinitionId;
  /** 流程模型编号 */
  private String modelId;
  /** 描述 */
  private String description;
  /** 表单类型 {@link BpmModelFormTypeEnum} */
  private Integer formType;
  /** 动态表单编号 表单类型为{@link BpmModelFormTypeEnum#NORMAL}时使用 */
  private Long formId;
  /** 表单配置 表单类型为{@link BpmModelFormTypeEnum#NORMAL}时使用 */
  private String formConf;
  /** 表单项数组 表单类型为{@link BpmModelFormTypeEnum#NORMAL}时使用 */
  @TableField(typeHandler = JacksonTypeHandler.class)
  private List<String> formFields;
  /** 自定义表单的提交路径，使用 Vue 的路由地址 在表单类型为 {@link BpmModelFormTypeEnum#CUSTOM} 时使用 */
  private String formCustomCreatePath;
  /** 自定义表单的查看路径，使用 Vue 的路由地址 在表单类型为 {@link BpmModelFormTypeEnum#CUSTOM} 时使用 */
  private String formCustomViewPath;
}
