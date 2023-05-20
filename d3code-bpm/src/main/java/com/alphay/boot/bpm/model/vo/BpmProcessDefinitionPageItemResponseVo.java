package com.alphay.boot.bpm.model.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * BPM 流程定义 分页项目 vo
 *
 * @author Nottyjay
 */
@Data
public class BpmProcessDefinitionPageItemResponseVo extends BpmProcessDefinitionResponseVo {

  private String formName;

  private LocalDateTime deploymentTime;
}
