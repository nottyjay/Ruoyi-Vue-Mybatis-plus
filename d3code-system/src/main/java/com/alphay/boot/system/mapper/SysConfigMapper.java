package com.alphay.boot.system.mapper;

import com.alphay.boot.common.mybatis.mapper.BaseMapperX;
import com.alphay.boot.system.common.domain.SysConfig;

import java.util.List;

/**
 * 参数配置 数据层
 *
 * @author d3code
 */
public interface SysConfigMapper extends BaseMapperX<SysConfig> {
  /**
   * 查询参数配置信息
   *
   * @param config 参数配置信息
   * @return 参数配置信息
   */
  SysConfig selectConfig(SysConfig config);

  /**
   * 通过ID查询配置
   *
   * @param configId 参数ID
   * @return 参数配置信息
   */
  SysConfig selectConfigById(Long configId);

  /**
   * 查询参数配置列表
   *
   * @param config 参数配置信息
   * @return 参数配置集合
   */
  List<SysConfig> selectConfigList(SysConfig config);

  /**
   * 根据键名查询参数配置信息
   *
   * @param configKey 参数键名
   * @return 参数配置信息
   */
  SysConfig checkConfigKeyUnique(String configKey);
}
