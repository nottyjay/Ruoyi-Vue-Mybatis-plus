package com.alphay.boot.system.common.service;

import com.alphay.boot.system.common.domain.SysNotice;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 公告 服务层
 *
 * @author d3code
 */
public interface ISysNoticeService extends IService<SysNotice> {

  /**
   * 查询公告列表
   *
   * @param notice 公告信息
   * @return 公告集合
   */
  default List<SysNotice> selectNoticeList(SysNotice notice) {
    return selectNoticeList(notice, null);
  }

  /**
   * 查询公告列表
   *
   * @param notice 公告信息
   * @param page 分页信息
   * @return 公告集合
   */
  List<SysNotice> selectNoticeList(SysNotice notice, IPage page);
}
