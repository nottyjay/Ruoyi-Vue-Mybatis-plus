package com.alphay.boot.bpm.domain;

import com.alphay.boot.bpm.enums.BpmProcessInstanceResultEnum;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.alphay.boot.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Bpm 流程任务的拓展表 主要解决 Flowable Task 和 HistoricTaskInstance 不支持拓展字段，所以新建拓展表
 *
 * @author Nottyjay
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(autoResultMap = true)
public class BpmTaskExt extends BaseEntity {

  /** 编号，自增 */
  @TableId(type = IdType.AUTO)
  private Long id;

  /**
   * 任务的审批人
   *
   * <p>冗余 Task 的 assignee 属性
   */
  private Long assigneeUserId;
  /**
   * 任务的名字
   *
   * <p>冗余 Task 的 name 属性，为了筛选
   */
  private String name;
  /**
   * 任务的编号
   *
   * <p>关联 Task 的 id 属性
   */
  private String taskId;
  //    /**
  //     * 任务的标识
  //     *
  //     * 关联 {@link Task#getTaskDefinitionKey()}
  //     */
  //    private String definitionKey;
  /**
   * 任务的结果
   *
   * <p>枚举 {@link BpmProcessInstanceResultEnum}
   */
  private Integer result;
  /** 审批建议 */
  private String reason;
  /**
   * 任务的结束时间
   *
   * <p>冗余 HistoricTaskInstance 的 endTime 属性
   */
  private LocalDateTime endTime;

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
}
