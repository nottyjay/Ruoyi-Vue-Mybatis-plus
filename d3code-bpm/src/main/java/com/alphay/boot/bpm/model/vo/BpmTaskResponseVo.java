package com.alphay.boot.bpm.model.vo;

import lombok.Data;

@Data
public class BpmTaskResponseVo extends BpmTaskDoneItemResponseVo {

  private String definitionKey;

  /** 审核的用户信息 */
  private User assigneeUser;

  @Data
  public static class User {

    private Long id;
    private String nickname;

    private Long deptId;
    private String deptName;
  }
}
