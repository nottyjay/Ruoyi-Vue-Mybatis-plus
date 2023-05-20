package com.alphay.boot.bpm.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.alphay.boot.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * 业务流表单对象 bpm_form
 *
 * @author ruoyi
 * @date 2023-04-07
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(autoResultMap = true)
public class BpmForm extends BaseEntity {
  private static final long serialVersionUID = 1L;

  /** 序号 */
  @TableId(type = IdType.AUTO)
  private Long id;
  /** 名称 */
  private String name;
  /** 状态 */
  private Integer status;
  /** 配置项 */
  private String conf;
  /** 字段列表 */
  @TableField(typeHandler = JacksonTypeHandler.class)
  private List<String> fields;

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
        .append("status", getStatus())
        .append("conf", getConf())
        .append("fields", getFields())
        .append("remark", getRemark())
        .toString();
  }
}
