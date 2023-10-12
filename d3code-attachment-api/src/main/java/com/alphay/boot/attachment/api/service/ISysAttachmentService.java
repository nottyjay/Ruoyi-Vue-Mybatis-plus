package com.alphay.boot.attachment.api.service;

import com.alphay.boot.attachment.api.domain.SysAttachment;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 系统附件管理
 *
 * @author D3code
 */
public interface ISysAttachmentService extends IService<SysAttachment> {

  /**
   * 查询文件附件列表
   *
   * @param attachment 文件管理
   * @param page 分页控制器
   * @return 文件集合
   */
  List<SysAttachment> selectSysAttachmentList(SysAttachment attachment, IPage<SysAttachment> page);
}
