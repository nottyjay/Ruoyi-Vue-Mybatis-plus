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
 * 存储桶对象 sys_oss_bucket
 *
 * @author d3code
 * @date 2023-10-13
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysOssBucket extends BaseEntity {
  private static final long serialVersionUID = 1L;

  /** 序号 */
  @TableId(type = IdType.AUTO)
  private Long id;

  /** 存储方式 */
  @Excel(name = "存储方式")
  private Long ossConfigId;

  /** 桶名 */
  @Excel(name = "桶名")
  private String bucket;

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
        .append("id", getId())
        .append("createTime", getCreateTime())
        .append("createBy", getCreateBy())
        .append("updateTime", getUpdateTime())
        .append("updateBy", getUpdateBy())
        .append("deleted", getDeleted())
        .append("ossConfigId", getOssConfigId())
        .append("bucket", getBucket())
        .append("remark", getRemark())
        .toString();
  }
}
