package com.alphay.boot.system.common.service;

import com.alphay.boot.system.common.domain.SysConfig;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 参数配置 服务层
 *
 * @author d3code
 */
public interface ISysConfigService extends IService<SysConfig> {

  /**
   * 根据键名查询参数配置信息
   *
   * @param configKey 参数键名
   * @return 参数键值
   */
  String selectConfigByKey(String configKey);

  /**
   * 获取验证码开关
   *
   * @return true开启，false关闭
   */
  boolean selectCaptchaEnabled();

  /**
   * 查询参数配置列表
   *
   * @param config 参数配置信息
   * @return 参数配置集合
   */
  List<SysConfig> selectConfigList(SysConfig config, IPage page);

  /** 加载参数缓存数据 */
  void loadingConfigCache();

  /** 清空参数缓存数据 */
  void clearConfigCache();

  /** 重置参数缓存数据 */
  void resetConfigCache();

  /**
   * 校验参数键名是否唯一
   *
   * @param config 参数信息
   * @return 结果
   */
  String checkConfigKeyUnique(SysConfig config);

  /**
   * 通过key获取config配置
   *
   * @param configKey
   * @return
   */
  SysConfig getConfigByKey(String configKey);
}
