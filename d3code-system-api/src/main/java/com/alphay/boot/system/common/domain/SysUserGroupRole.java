package com.alphay.boot.system.common.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sys_group_role")
public class SysUserGroupRole {

  private Long groupId;
  private Long roleId;
}
