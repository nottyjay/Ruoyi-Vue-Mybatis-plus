package com.alphay.boot.bpm.model.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BpmProcessInstanceItemResponseVo {

  private String id;

  private String name;

  private String processDefinitionId;

  private String category;

  private Integer status;

  private Integer result;

  private LocalDateTime createTime;

  private LocalDateTime endTime;

  /** 当前任务 */
  private List<Task> tasks;

  @Data
  public static class Task {

    private String id;

    private String name;
  }
}
