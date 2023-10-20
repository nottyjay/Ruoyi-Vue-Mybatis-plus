package com.alphay.boot.attachment.service.impl;

import java.util.List;

import com.alphay.boot.attachment.api.bean.TencentCosConfig;
import com.alphay.boot.attachment.api.domain.SysOssBucket;
import com.alphay.boot.attachment.api.enums.StorageType;
import com.alphay.boot.attachment.api.service.ISysOssBucketService;
import com.alphay.boot.attachment.storage.StorageEngine;
import com.alphay.boot.attachment.storage.StorageEngineFactory;
import com.alphay.boot.attachment.utils.StorageEngineUtil;
import com.alphay.boot.common.enums.SystemStatusEnum;
import com.alphay.boot.common.utils.JsonUtil;
import com.alphay.boot.common.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.alphay.boot.common.mybatis.service.ServiceImplX;
import com.alphay.boot.attachment.mapper.SysOssConfigMapper;
import com.alphay.boot.attachment.api.domain.SysOssConfig;
import com.alphay.boot.attachment.api.service.ISysOssConfigService;

import javax.annotation.Resource;

/**
 * 存储配置Service业务层处理
 *
 * @author d3code
 * @date 2023-10-13
 */
@Service
public class SysOssConfigServiceImpl extends ServiceImplX<SysOssConfigMapper, SysOssConfig>
    implements ISysOssConfigService {

  @Resource private ISysOssBucketService ossBucketService;

  /**
   * 查询存储配置列表
   *
   * @param sysOssConfig 存储配置
   * @param page 分页
   * @return 存储配置
   */
  @Override
  public List<SysOssConfig> selectSysOssConfigList(SysOssConfig sysOssConfig, IPage page) {
    return this.list(
        page,
        this.lambdaQueryWrapperX()
            .likeIfPresent(SysOssConfig::getName, sysOssConfig.getName())
            .eqIfPresent(SysOssConfig::getOssType, sysOssConfig.getOssType()));
  }

  @Override
  public boolean save(SysOssConfig entity) {
    boolean result = super.save(entity);
    if (result) {
      if (StringUtils.equals(SystemStatusEnum.ENABLE.getStatus(), entity.getStatus())) {
        switchStorageEngine(entity.getId());
      }
    }
    return result;
  }

  @Override
  public boolean updateById(SysOssConfig entity) {
    SysOssConfig oldConfig = this.getById(entity.getId());
    boolean result = super.updateById(entity);
    if (result) {
      if (StringUtils.equals(SystemStatusEnum.ENABLE.getStatus(), entity.getStatus())
          && StringUtils.equals(SystemStatusEnum.DISABLE.getStatus(), oldConfig.getStatus())) {
        switchStorageEngine(entity.getId());
      }
    }
    return result;
  }

  @Override
  public void switchStorageEngine(Long id) {
    SysOssConfig config = this.getById(id);
    this.update(
        new LambdaUpdateWrapper<SysOssConfig>()
            .ne(SysOssConfig::getId, id)
            .set(SysOssConfig::getStatus, SystemStatusEnum.DISABLE.getStatus()));

    StorageEngine storageEngine = StorageEngineUtil.getInstance(config);
    String buckname = (String) JsonUtil.toMap(config.getConfig()).get("bucketName");
    // 检查buck是否已经创建
    boolean isExit = storageEngine.exitsBucket(buckname);
    if (!isExit) {
      ossBucketService.save(
          SysOssBucket.builder().ossConfigId(config.getId()).bucket(buckname).build());
      storageEngine.createBucket(buckname);
    }
  }

  @Override
  public SysOssConfig getEnabledConfig() {
    return this.getOne(
        this.lambdaQueryWrapperX()
            .eq(SysOssConfig::getStatus, SystemStatusEnum.ENABLE.getStatus()));
  }
}
