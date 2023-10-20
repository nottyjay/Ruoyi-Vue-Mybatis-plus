package com.alphay.boot.attachment.api.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.alphay.boot.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.alphay.boot.common.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;

/**
 * 存储配置对象 sys_oss_config
 *
 * @author d3code
 * @date 2023-10-13
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysOssConfig extends BaseEntity {
  private static final long serialVersionUID = 1L;

  /** 序号 */
  @TableId(type = IdType.AUTO)
  private Long id;

  /** 配置名称 */
  @Excel(name = "配置名称")
  private String name;

  /** 存储类型 */
  @Excel(name = "存储类型")
  private String ossType;

  /** 存储内容 */
  private String config;

  /** 配置状态 */
  private String status;

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
        .append("ossType", getOssType())
        .append("config", getConfig())
        .append("status", getStatus())
        .append("remark", getRemark())
        .toString();
  }
}
