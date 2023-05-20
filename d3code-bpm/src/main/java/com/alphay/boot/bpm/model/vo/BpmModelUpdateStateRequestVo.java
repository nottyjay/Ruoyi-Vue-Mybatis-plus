package com.alphay.boot.bpm.model.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 模型状态更新 vo
 *
 * @author Nottyjay
 */
@Data
public class BpmModelUpdateStateRequestVo {

  @NotNull(message = "编号不能为空")
  private String id;

  @NotNull(message = "状态不能为空")
  private Integer state;
}
