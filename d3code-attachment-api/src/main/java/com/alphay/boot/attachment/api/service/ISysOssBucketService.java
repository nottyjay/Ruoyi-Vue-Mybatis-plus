package com.alphay.boot.attachment.api.service;

import java.util.List;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.alphay.boot.attachment.api.domain.SysOssBucket;

/**
 * 存储桶Service接口
 *
 * @author d3code
 * @date 2023-10-13
 */
public interface ISysOssBucketService extends IService<SysOssBucket> {

  /**
   * 查询存储桶列表
   *
   * @param sysOssBucket 存储桶
   * @return 存储桶集合
   */
  default List<SysOssBucket> selectSysOssBucketList(SysOssBucket sysOssBucket) {
    return selectSysOssBucketList(sysOssBucket, null);
  }

  /**
   * 查询存储桶列表
   *
   * @param sysOssBucket 存储桶
   * @param page 分页
   * @return 存储桶集合
   */
  List<SysOssBucket> selectSysOssBucketList(SysOssBucket sysOssBucket, IPage page);
}
