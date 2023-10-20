package com.alphay.boot.attachment.service.impl;

import java.util.List;

import com.alphay.boot.attachment.storage.StorageEngine;
import com.alphay.boot.attachment.utils.StorageEngineUtil;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.alphay.boot.common.mybatis.service.ServiceImplX;
import com.alphay.boot.attachment.mapper.SysOssBucketMapper;
import com.alphay.boot.attachment.api.domain.SysOssBucket;
import com.alphay.boot.attachment.api.service.ISysOssBucketService;

/**
 * 存储桶Service业务层处理
 *
 * @author d3code
 * @date 2023-10-13
 */
@Service
public class SysOssBucketServiceImpl extends ServiceImplX<SysOssBucketMapper, SysOssBucket>
    implements ISysOssBucketService {

  /**
   * 查询存储桶列表
   *
   * @param sysOssBucket 存储桶
   * @param page 分页
   * @return 存储桶
   */
  @Override
  public List<SysOssBucket> selectSysOssBucketList(SysOssBucket sysOssBucket, IPage page) {
    return this.list(page, this.lambdaQueryWrapperX());
  }

  @Override
  public boolean save(SysOssBucket entity) {
    StorageEngine engine = StorageEngineUtil.getInstance();
    engine.createBucket(entity.getBucket());
    return super.save(entity);
  }
}
