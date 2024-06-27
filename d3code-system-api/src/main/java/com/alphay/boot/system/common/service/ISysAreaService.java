package com.alphay.boot.system.common.service;

import java.util.List;

import com.alphay.boot.common.core.domain.TreeSelect;
import com.alphay.boot.common.core.domain.entity.SysArea;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 地区管理Service接口
 *
 * @author d3code
 * @date 2024-06-21
 */
public interface ISysAreaService extends IService<SysArea> {

  /**
   * 查询地区管理列表
   *
   * @param sysArea 地区管理
   * @return 地区管理集合
   */
  default List<SysArea> selectSysAreaList(SysArea sysArea) {
    return selectSysAreaList(sysArea, null);
  }

  /**
   * 查询地区管理列表
   *
   * @param sysArea 地区管理
   * @param page 分页
   * @return 地区管理集合
   */
  List<SysArea> selectSysAreaList(SysArea sysArea, IPage page);

  /**
   * 查询地区管理树状菜单列表
   *
   * @param sysArea 地区管理
   * @return
   */
  List<TreeSelect> selectSysAreaTreeList(SysArea sysArea);

  /**
   * 同步地区
   *
   * @param content
   */
  void syncArea(String content);

  /** 获取同步进度 */
  Double getSyncProcess();
}
