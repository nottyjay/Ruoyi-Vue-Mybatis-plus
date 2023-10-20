package com.alphay.boot.attachment.api.service;

import java.util.List;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.alphay.boot.attachment.api.domain.SysAttachment;

/**
 * 文件管理Service接口
 *
 * @author d3code
 * @date 2023-10-13
 */
public interface ISysAttachmentService extends IService<SysAttachment> {

  /**
   * 查询文件管理列表
   *
   * @param sysAttachment 文件管理
   * @return 文件管理集合
   */
  default List<SysAttachment> selectSysAttachmentList(SysAttachment sysAttachment) {
    return selectSysAttachmentList(sysAttachment, null);
  }

  /**
   * 查询文件管理列表
   *
   * @param sysAttachment 文件管理
   * @param page 分页
   * @return 文件管理集合
   */
  List<SysAttachment> selectSysAttachmentList(SysAttachment sysAttachment, IPage page);
}
