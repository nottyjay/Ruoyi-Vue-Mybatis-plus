package com.alphay.boot.system.common.service;

import java.util.List;

import com.alphay.boot.common.core.domain.entity.SysDictData;
import com.alphay.boot.common.core.domain.entity.SysDictType;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 字典 业务层
 *
 * @author d3code
 */
public interface ISysDictTypeService extends IService<SysDictType> {
  /**
   * 根据条件分页查询字典类型
   *
   * @param dictType 字典类型信息
   * @return 字典类型集合信息
   */
  List<SysDictType> selectDictTypeList(SysDictType dictType, IPage page);

  /**
   * 根据所有字典类型
   *
   * @return 字典类型集合信息
   */
  List<SysDictType> selectDictTypeAll();

  /**
   * 根据字典类型查询字典数据
   *
   * @param dictType 字典类型
   * @return 字典数据集合信息
   */
  List<SysDictData> selectDictDataByType(String dictType);

  /**
   * 根据字典类型查询信息
   *
   * @param dictType 字典类型
   * @return 字典类型
   */
  SysDictType getOneByType(String dictType);

  /**
   * 批量删除字典信息
   *
   * @param dictIds 需要删除的字典ID
   */
  void deleteDictTypeByIds(Long[] dictIds);

  /** 加载字典缓存数据 */
  void loadingDictCache();

  /** 清空字典缓存数据 */
  void clearDictCache();

  /** 重置字典缓存数据 */
  void resetDictCache();

  /**
   * 校验字典类型称是否唯一
   *
   * @param dictType 字典类型
   * @return 结果
   */
  String checkDictTypeUnique(SysDictType dictType);
}
