package com.alphay.boot.attachment.service.impl;

import java.util.List;

import com.alphay.boot.common.annotation.DataScope;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.alphay.boot.common.mybatis.service.ServiceImplX;
import com.alphay.boot.attachment.mapper.SysAttachmentMapper;
import com.alphay.boot.attachment.api.domain.SysAttachment;
import com.alphay.boot.attachment.api.service.ISysAttachmentService;

/**
 * 文件管理Service业务层处理
 *
 * @author d3code
 * @date 2023-10-13
 */
@Service
public class SysAttachmentServiceImpl extends ServiceImplX<SysAttachmentMapper, SysAttachment>
    implements ISysAttachmentService {

  /**
   * 查询文件管理列表
   *
   * @param sysAttachment 文件管理
   * @param page 分页
   * @return 文件管理
   */
  @Override
  @DataScope(userAlias = "user_id")
  public List<SysAttachment> selectSysAttachmentList(SysAttachment sysAttachment, IPage page) {
    return this.list(
        page,
        this.lambdaQueryWrapperX()
            .eqIfPresent(SysAttachment::getCreateBy, sysAttachment.getCreateBy())
            .eqIfPresent(SysAttachment::getConfigId, sysAttachment.getConfigId())
            .likeIfPresent(SysAttachment::getName, sysAttachment.getName())
            .eqIfPresent(SysAttachment::getStorageType, sysAttachment.getStorageType())
            .last(sysAttachment.getParamsDataScope()));
  }

  @Override
  public List<SysAttachment> selectSysAttachmentListByIds(List<Long> ids) {
    return this.list(this.lambdaQueryWrapperX().inIfPresent(SysAttachment::getId, ids));
  }
}
