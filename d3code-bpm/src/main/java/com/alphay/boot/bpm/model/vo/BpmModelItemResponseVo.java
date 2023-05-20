package com.alphay.boot.bpm.model.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * BPM 模型定义
 *
 * @author Nottyjay
 */
@Data
public class BpmModelItemResponseVo extends BpmModelBaseVO {
  private String id;
  private String formName;
  private LocalDateTime createTime;
  private ProcessDefinition processDefinition;

  @Data
  public static class ProcessDefinition {

    private String id;

    private Integer version;

    private LocalDateTime deploymentTime;

    private Integer suspensionState;
  }
}
