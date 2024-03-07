package com.alphay.boot.system.service.impl;

import java.util.Collection;
import java.util.List;

import com.alphay.boot.common.constant.CacheConstants;
import com.alphay.boot.common.core.redis.RedisCache;
import com.alphay.boot.common.core.text.Convert;
import com.alphay.boot.common.exception.ServiceException;
import com.alphay.boot.common.mybatis.query.QueryWrapperX;
import com.alphay.boot.common.mybatis.service.ServiceImplX;
import com.alphay.boot.system.common.domain.SysConfig;
import com.alphay.boot.system.common.service.ISysConfigService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alphay.boot.common.constant.UserConstants;
import com.alphay.boot.common.utils.StringUtils;
import com.alphay.boot.system.mapper.SysConfigMapper;

/**
 * 参数配置 服务层实现
 *
 * @author d3code
 */
@Service
public class SysConfigServiceImpl extends ServiceImplX<SysConfigMapper, SysConfig>
    implements ISysConfigService {

  @Autowired private RedisCache redisCache;

  /**
   * 根据键名查询参数配置信息
   *
   * @param configKey 参数key
   * @return 参数键值
   */
  @Override
  public String selectConfigByKey(String configKey) {
    String configValue = Convert.toStr(redisCache.getCacheObject(getCacheKey(configKey)));
    if (StringUtils.isNotEmpty(configValue)) {
      return configValue;
    }
    SysConfig retConfig = this.getConfigByKey(configKey);
    if (StringUtils.isNotNull(retConfig)) {
      redisCache.setCacheObject(getCacheKey(configKey), retConfig.getConfigValue());
      return retConfig.getConfigValue();
    }
    return StringUtils.EMPTY;
  }

  /**
   * 获取验证码开关
   *
   * @return true开启，false关闭
   */
  @Override
  public boolean selectCaptchaEnabled() {
    String captchaEnabled = selectConfigByKey("sys.account.captchaEnabled");
    if (StringUtils.isEmpty(captchaEnabled)) {
      return true;
    }
    return Convert.toBool(captchaEnabled);
  }

  /**
   * 查询参数配置列表
   *
   * @param config 参数配置信息
   * @return 参数配置集合
   */
  @Override
  public List<SysConfig> selectConfigList(SysConfig config, IPage page) {
    QueryWrapperX<SysConfig> queryWrapper =
        new QueryWrapperX<SysConfig>()
            .likeIfPresent("config_name", config.getConfigName())
            .eqIfPresent("config_type", config.getConfigType())
            .likeIfPresent("config_key", config.getConfigKey());
    parseBeginTimeAndEndTime(config.getParams(), queryWrapper, "create_time");
    queryWrapper.orderByDesc("config_id");
    return this.list(page, queryWrapper);
  }

  /**
   * 新增参数配置
   *
   * @param config 参数配置信息
   * @return 结果
   */
  @Override
  public boolean save(SysConfig config) {
    boolean result = super.save(config);
    if (result) {
      redisCache.setCacheObject(getCacheKey(config.getConfigKey()), config.getConfigValue());
    }
    return result;
  }

  /**
   * 修改参数配置
   *
   * @param config 参数配置信息
   * @return 结果
   */
  @Override
  public boolean updateById(SysConfig config) {
    SysConfig temp = this.getById(config.getConfigId());
    if (!StringUtils.equals(temp.getConfigKey(), config.getConfigKey())) {
      redisCache.deleteObject(getCacheKey(temp.getConfigKey()));
    }

    boolean result = super.updateById(config);
    if (result) {
      redisCache.setCacheObject(getCacheKey(config.getConfigKey()), config.getConfigValue());
    }
    return result;
  }

  @Override
  public boolean removeByIds(Collection<?> configIds) {
    configIds.stream()
        .map(configId -> this.getById((Long) configId))
        .forEach(
            config -> {
              if (StringUtils.equals(UserConstants.YES, config.getConfigType())) {
                throw new ServiceException(String.format("内置参数【%1$s】不能删除 ", config.getConfigKey()));
              }
              this.removeById(config.getConfigId());
              redisCache.deleteObject(getCacheKey(config.getConfigKey()));
            });
    return true;
  }

  /** 加载参数缓存数据 */
  @Override
  public void loadingConfigCache() {
    List<SysConfig> configsList = this.selectConfigList(new SysConfig(), null);
    for (SysConfig config : configsList) {
      redisCache.setCacheObject(getCacheKey(config.getConfigKey()), config.getConfigValue());
    }
  }

  /** 清空参数缓存数据 */
  @Override
  public void clearConfigCache() {
    Collection<String> keys = redisCache.keys(CacheConstants.SYS_CONFIG_KEY + "*");
    redisCache.deleteObject(keys);
  }

  /** 重置参数缓存数据 */
  @Override
  public void resetConfigCache() {
    clearConfigCache();
    loadingConfigCache();
  }

  /**
   * 校验参数键名是否唯一
   *
   * @param config 参数配置信息
   * @return 结果
   */
  @Override
  public String checkConfigKeyUnique(SysConfig config) {
    Long configId = StringUtils.isNull(config.getConfigId()) ? -1L : config.getConfigId();
    SysConfig info =
        this.getOne(this.lambdaQuery().eq(SysConfig::getConfigKey, config.getConfigKey()));
    if (StringUtils.isNotNull(info) && info.getConfigId().longValue() != configId.longValue()) {
      return UserConstants.NOT_UNIQUE;
    }
    return UserConstants.UNIQUE;
  }

  @Override
  public SysConfig getConfigByKey(String configKey) {
    return this.getOne(this.lambdaQueryWrapperX().eq(SysConfig::getConfigKey, configKey));
  }

  /**
   * 设置cache key
   *
   * @param configKey 参数键
   * @return 缓存键key
   */
  private String getCacheKey(String configKey) {
    return CacheConstants.SYS_CONFIG_KEY + configKey;
  }
}
