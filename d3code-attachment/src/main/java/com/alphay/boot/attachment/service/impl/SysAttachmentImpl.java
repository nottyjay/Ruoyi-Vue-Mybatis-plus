package com.alphay.boot.attachment.service.impl;

import com.alphay.boot.attachment.api.domain.SysAttachment;
import com.alphay.boot.attachment.api.service.ISysAttachmentService;
import com.alphay.boot.attachment.mapper.SysAttachmentMapper;
import com.alphay.boot.common.mybatis.query.LambdaQueryWrapperX;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysAttachmentImpl extends ServiceImpl<SysAttachmentMapper, SysAttachment>
    implements ISysAttachmentService {

  @Override
  public List<SysAttachment> selectSysAttachmentList(
      SysAttachment attachment, IPage<SysAttachment> page) {
    return this.list(
        page,
        new LambdaQueryWrapperX<SysAttachment>()
            .likeIfPresent(SysAttachment::getName, attachment.getName())
            .eqIfPresent(SysAttachment::getStorageType, attachment.getStorageType()));
  }
}
