package com.alphay.boot.system.common.domain;

import lombok.Data;

@Data
public class SysUserGroupRelation {
  private Long userId;
  private Long groupId;
}
