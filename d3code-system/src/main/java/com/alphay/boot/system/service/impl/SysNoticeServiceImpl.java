package com.alphay.boot.system.service.impl;

import java.util.List;

import com.alphay.boot.common.mybatis.service.ServiceImplX;
import com.alphay.boot.system.common.domain.SysNotice;
import com.alphay.boot.system.common.service.ISysNoticeService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alphay.boot.system.mapper.SysNoticeMapper;

/**
 * 公告 服务层实现
 *
 * @author d3code
 */
@Service
public class SysNoticeServiceImpl extends ServiceImplX<SysNoticeMapper, SysNotice>
    implements ISysNoticeService {

  /**
   * 查询公告列表
   *
   * @param notice 公告信息
   * @return 公告集合
   */
  @Override
  public List<SysNotice> selectNoticeList(SysNotice notice, IPage page) {
    return this.list(
        page,
        this.lambdaQueryWrapperX()
            .likeIfPresent(SysNotice::getNoticeTitle, notice.getNoticeTitle())
            .likeIfPresent(SysNotice::getCreateBy, notice.getCreateBy())
            .eqIfPresent(SysNotice::getNoticeType, notice.getNoticeType()));
  }
}
