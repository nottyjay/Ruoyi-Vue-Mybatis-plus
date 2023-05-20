package com.alphay.boot.bpm.model.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BpmTaskTodoItemResponseVo {

  private String id;

  private String name;

  private LocalDateTime claimTime;

  private LocalDateTime createTime;

  private Integer suspensionState;

  /** 所属流程实例 */
  private ProcessInstance processInstance;

  @Data
  public static class ProcessInstance {

    private String id;

    private String name;

    private Long startUserId;

    private String startUserNickname;

    private String processDefinitionId;
  }
}
