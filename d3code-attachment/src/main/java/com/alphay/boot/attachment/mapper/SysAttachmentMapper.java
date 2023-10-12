package com.alphay.boot.attachment.mapper;

import com.alphay.boot.attachment.api.domain.SysAttachment;
import com.alphay.boot.common.mybatis.mapper.BaseMapperX;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 文件管理Mapper
 *
 * @author d3code
 */
public interface SysAttachmentMapper extends BaseMapperX<SysAttachment> {

  /**
   * 获取文件列表
   *
   * @param attachment
   * @param page
   * @return
   */
  List<SysAttachment> selectSysAttachmentList(SysAttachment attachment, IPage<SysAttachment> page);
}
