package com.alphay.boot.system.common.domain;

import com.alphay.boot.common.core.domain.entity.SysUser;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.alphay.boot.common.core.domain.BaseEntity;
import com.alphay.boot.common.mybatis.typehandler.JsonLongSetTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

/** 用户分组 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(autoResultMap = true)
public class SysUserGroup extends BaseEntity {

  @TableId(type = IdType.AUTO)
  private Long id;

  /** 组名 */
  private String name;

  /** 描述 */
  private String description;

  /** 状态 */
  private String status;

  /** 成员用户编号数组 */
  @TableField(exist = false)
  private Set<Long> memberUserIds;

  /** 成员用户 */
  @TableField(exist = false)
  private List<SysUser> users;
}
