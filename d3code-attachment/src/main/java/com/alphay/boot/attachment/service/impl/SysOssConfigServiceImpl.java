package com.alphay.boot.attachment.service.impl;

import java.util.List;

import com.alphay.boot.attachment.storage.StorageEngine;
import com.alphay.boot.attachment.utils.StorageEngineUtil;
import com.alphay.boot.common.enums.SystemStatusEnum;
import com.alphay.boot.common.mybatis.query.LambdaQueryWrapperX;
import com.alphay.boot.common.utils.JsonUtil;
import com.alphay.boot.common.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.alphay.boot.common.mybatis.service.ServiceImplX;
import com.alphay.boot.attachment.mapper.SysOssConfigMapper;
import com.alphay.boot.attachment.api.domain.SysOssConfig;
import com.alphay.boot.attachment.api.service.ISysOssConfigService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 存储配置Service业务层处理
 *
 * @author d3code
 * @date 2023-10-13
 */
@Service
public class SysOssConfigServiceImpl extends ServiceImplX<SysOssConfigMapper, SysOssConfig>
    implements ISysOssConfigService {

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
    boolean result = super.updateById(entity);
    if (result) {
      switchStorageEngine(entity.getId());
    }
    return result;
  }

  @Override
  @Transactional
  public void switchStorageEngine(Long id) {
    SysOssConfig config = this.getById(id);
    if (StringUtils.equals(SystemStatusEnum.DISABLE.getStatus(), config.getStatus())) {
      super.updateById(
          SysOssConfig.builder().id(id).status(SystemStatusEnum.ENABLE.getStatus()).build());
    }
    this.update(
        new LambdaUpdateWrapper<SysOssConfig>()
            .ne(SysOssConfig::getId, id)
            .set(SysOssConfig::getStatus, SystemStatusEnum.DISABLE.getStatus()));

    StorageEngine storageEngine = StorageEngineUtil.getInstance(config);
    String buckname = (String) JsonUtil.toMap(config.getConfig()).get("bucketName");
    // 检查buck是否已经创建
    boolean isExit = storageEngine.exitsBucket(buckname);
    if (!isExit) {
      storageEngine.createBucket(buckname);
    }
  }

  @Override
  public SysOssConfig getEnabledConfig() {
    return this.getOne(
        this.lambdaQueryWrapperX()
            .eq(SysOssConfig::getStatus, SystemStatusEnum.ENABLE.getStatus()));
  }

  @Override
  public void refreshEngine() {
    SysOssConfig config = this.getEnabledConfig();
    StorageEngine storageEngine = StorageEngineUtil.getInstance(config);
    String buckname = (String) JsonUtil.toMap(config.getConfig()).get("bucketName");
    // 检查buck是否已经创建
    boolean isExit = storageEngine.exitsBucket(buckname);
    if (!isExit) {
      storageEngine.createBucket(buckname);
    }
  }
}
