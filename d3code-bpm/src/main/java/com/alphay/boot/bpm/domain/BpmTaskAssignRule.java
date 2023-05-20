package com.alphay.boot.bpm.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.alphay.boot.common.core.domain.BaseEntity;
import com.alphay.boot.common.mybatis.typehandler.JsonLongSetTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@TableName(autoResultMap = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BpmTaskAssignRule extends BaseEntity {

  /** {@link #processDefinitionId} 空串，用于标识属于流程模型，而不属于流程定义 */
  public static final String PROCESS_DEFINITION_ID_NULL = "";

  @TableId(type = IdType.AUTO)
  private Long id;

  /** 流程模型编号 */
  private String modelId;

  /** 流程定义编号 */
  private String processDefinitionId;

  /** 流程任务的定义 */
  private String taskDefinitionKey;

  /** 规则类型 */
  @TableField("`type`")
  private Integer type;

  /** 规则值数组，一般关联指定表的编号 根据 type 不同，对应的值是不同的 */
  @TableField(typeHandler = JsonLongSetTypeHandler.class)
  private Set<Long> options;
}
