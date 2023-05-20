package com.d3code.bpm.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.d3code.common.core.domain.BaseEntity;
import com.d3code.common.mybatis.typehandler.JsonLongSetTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/** 用户分组 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(autoResultMap = true)
public class BpmUserGroup extends BaseEntity {

  @TableId(type = IdType.AUTO)
  private Long id;
  /** 组名 */
  private String name;
  /** 描述 */
  private String description;
  /** 状态 */
  private Integer status;
  /** 成员用户编号数组 */
  @TableField(typeHandler = JsonLongSetTypeHandler.class)
  private Set<Long> memberUserIds;
}
