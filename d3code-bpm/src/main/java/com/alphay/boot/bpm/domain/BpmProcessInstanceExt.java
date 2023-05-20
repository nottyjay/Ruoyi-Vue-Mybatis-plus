package com.alphay.boot.bpm.domain;

import com.alphay.boot.bpm.enums.BpmProcessInstanceResultEnum;
import com.alphay.boot.bpm.enums.BpmProcessInstanceStatusEnum;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.alphay.boot.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 流程实例扩展
 *
 * @author Nottyjay
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(autoResultMap = true)
public class BpmProcessInstanceExt extends BaseEntity {

  /** 编号，自增 */
  @TableId(type = IdType.AUTO)
  private Long id;
  /**
   * 发起流程的用户编号
   *
   * <p>冗余 HistoricProcessInstance 的 startUserId 属性
   */
  private Long startUserId;
  /**
   * 流程实例的名字
   *
   * <p>冗余 ProcessInstance 的 name 属性，用于筛选
   */
  private String name;
  /**
   * 流程实例的编号
   *
   * <p>关联 ProcessInstance 的 id 属性
   */
  private String processInstanceId;
  /**
   * 流程定义的编号
   *
   * <p>关联 ProcessDefinition 的 id 属性
   */
  private String processDefinitionId;
  /**
   * 流程分类
   *
   * <p>冗余 ProcessDefinition 的 category 属性 数据字典 bpm_model_category
   */
  private String category;
  /**
   * 流程实例的状态
   *
   * <p>枚举 {@link BpmProcessInstanceStatusEnum}
   */
  private Integer status;
  /**
   * 流程实例的结果
   *
   * <p>枚举 {@link BpmProcessInstanceResultEnum}
   */
  private Integer result;
  /**
   * 结束时间
   *
   * <p>冗余 HistoricProcessInstance 的 endTime 属性
   */
  private LocalDateTime endTime;

  /** 提交的表单值 */
  @TableField(typeHandler = JacksonTypeHandler.class)
  private Map<String, Object> formVariables;
}
