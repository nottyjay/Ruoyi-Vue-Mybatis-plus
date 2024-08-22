package com.alphay.boot.common.core.domain;

import com.baomidou.mybatisplus.annotation.TableField;

import java.util.ArrayList;
import java.util.List;

/**
 * Tree基类
 *
 * @author d3code
 */
public abstract class TreeEntity<T extends TreeEntity> extends BaseEntity {
  private static final long serialVersionUID = 1L;

  /** 祖级列表 */
  private String ancestors;

  /** 子部门 */
  @TableField(exist = false)
  private List<T> children = new ArrayList<>();

  public abstract Object getParentPKCode();

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
