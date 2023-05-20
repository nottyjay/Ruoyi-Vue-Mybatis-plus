package com.d3code.bpm.model.vo;

import lombok.Data;

/**
 * BPM 流程定义 查询vo
 *
 * @author Nottyjay
 */
@Data
public class BpmProcessDefinitionQueryRequestVo {

  private String key;

  private Integer suspensionState;
}
