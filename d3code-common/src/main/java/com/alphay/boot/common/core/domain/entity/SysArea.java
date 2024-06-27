package com.alphay.boot.common.core.domain.entity;

import com.alphay.boot.common.core.domain.TreeEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.alphay.boot.common.annotation.Excel;

/**
 * 地区管理对象 sys_area
 *
 * @author d3code
 * @date 2024-06-21
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysArea extends TreeEntity<SysArea> {
  private static final long serialVersionUID = 1L;

  /** 序号 */
  @TableId(type = IdType.AUTO)
  private Long id;

  @Excel(name = "地区名称")
  private String name;

  @Excel(name = "级别")
  private Integer level;

  @Excel(name = "行政区码")
  private String code;

  @Excel(name = "父地区行政区码")
  private String parentCode;

  @TableField(exist = false)
  private Boolean hasChildren;

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
        .append("id", getId())
        .append("createTime", getCreateTime())
        .append("createBy", getCreateBy())
        .append("updateTime", getUpdateTime())
        .append("updateBy", getUpdateBy())
        .append("deleted", getDeleted())
        .append("name", getName())
        .append("level", getLevel())
        .append("code", getCode())
        .append("parentName", getParentName())
        .append("parentId", getParentId())
        .append("parentCode", getParentCode())
        .append("ancestors", getAncestors())
        .append("orderNum", getOrderNum())
        .append("remark", getRemark())
        .toString();
  }
}
