package com.alphay.boot.bpm.model.vo;

import lombok.Data;

/**
 * 流程模型分页请求
 *
 * @author Nottyjay
 */
@Data
public class BpmModelQueryRequestVo {

  private String key;

  private String name;

  private String category;
}
