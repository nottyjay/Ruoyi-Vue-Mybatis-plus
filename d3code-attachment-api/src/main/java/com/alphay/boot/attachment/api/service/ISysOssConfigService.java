package com.alphay.boot.attachment.api.service;

import java.util.List;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.alphay.boot.attachment.api.domain.SysOssConfig;

/**
 * 存储配置Service接口
 *
 * @author d3code
 * @date 2023-10-13
 */
public interface ISysOssConfigService extends IService<SysOssConfig> {

  /**
   * 查询存储配置列表
   *
   * @param sysOssConfig 存储配置
   * @return 存储配置集合
   */
  default List<SysOssConfig> selectSysOssConfigList(SysOssConfig sysOssConfig) {
    return selectSysOssConfigList(sysOssConfig, null);
  }

  /**
   * 查询存储配置列表
   *
   * @param sysOssConfig 存储配置
   * @param page 分页
   * @return 存储配置集合
   */
  List<SysOssConfig> selectSysOssConfigList(SysOssConfig sysOssConfig, IPage page);

  /**
   * 切换目前在用的存储引擎
   *
   * @param id
   */
  void switchStorageEngine(Long id);

  /**
   * 获取当前启动的配置
   *
   * @return
   */
  SysOssConfig getEnabledConfig();
}
