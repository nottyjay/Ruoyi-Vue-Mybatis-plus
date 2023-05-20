package com.alphay.boot.bpm.model.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Bpm 流程模型VO
 *
 * @author Nottyjay
 */
@Data
public class BpmModelRespVo extends BpmModelBaseVO {
  private String id;
  private String bpmnXml;
  private LocalDateTime createTime;
}
