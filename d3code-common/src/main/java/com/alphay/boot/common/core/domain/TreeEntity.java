package com.alphay.boot.common.core.domain;

import com.baomidou.mybatisplus.annotation.TableField;

import java.util.ArrayList;
import java.util.List;

/**
 * Tree基类
 *
 * @author d3code
 */
public class TreeEntity<T extends TreeEntity> extends BaseEntity {
  private static final long serialVersionUID = 1L;

  /** 父菜单名称 */
  private String parentName;

  /** 父菜单ID */
  private Long parentId;

  /** 显示顺序 */
  private Integer orderNum;

  /** 祖级列表 */
  private String ancestors;

  /** 子部门 */
  @TableField(exist = false)
  private List<T> children = new ArrayList<>();

  public String getParentName() {
    return parentName;
  }

  public void setParentName(String parentName) {
    this.parentName = parentName;
  }

  public Long getParentId() {
    return parentId;
  }

  public void setParentId(Long parentId) {
    this.parentId = parentId;
  }

  public Integer getOrderNum() {
    return orderNum;
  }

  public void setOrderNum(Integer orderNum) {
    this.orderNum = orderNum;
  }

  public String getAncestors() {
    return ancestors;
  }

  public void setAncestors(String ancestors) {
    this.ancestors = ancestors;
  }

  public List<T> getChildren() {
    return children;
  }

  public void setChildren(List<T> children) {
    this.children = children;
  }
}
