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
 * 文件管理对象 sys_attachment
 *
 * @author d3code
 * @date 2023-10-12
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysAttachment extends BaseEntity {
  private static final long serialVersionUID = 1L;

  /** 序号 */
  @TableId(type = IdType.AUTO)
  private Long id;

  /** 文件名称 */
  @Excel(name = "文件名称")
  private String name;

  /** 存储方式 */
  @Excel(name = "存储方式")
  private String storageType;

  /** 后缀名 */
  private String extension;

  /** 存储路径 */
  @Excel(name = "存储路径")
  private String path;

  @Excel(name = "URL")
  private String url;

  @Excel(name = "桶名称")
  private String bucketName;

  /** 使用的存储配置 */
  private Long configId;

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
        .append("storageType", getStorageType())
        .append("path", getPath())
        .append("remark", getRemark())
        .toString();
  }
}
