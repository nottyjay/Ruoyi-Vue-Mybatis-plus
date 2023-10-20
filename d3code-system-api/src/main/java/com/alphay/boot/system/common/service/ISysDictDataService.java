package com.alphay.boot.system.common.service;

import java.util.Collection;
import java.util.List;

import com.alphay.boot.common.core.domain.entity.SysDictData;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 字典 业务层
 *
 * @author d3code
 */
public interface ISysDictDataService extends IService<SysDictData> {

  /**
   * 根据条件分页查询字典数据
   *
   * @param dictData 字典数据信息
   * @return 字典数据集合信息
   */
  default List<SysDictData> selectDictDataList(SysDictData dictData) {
    return selectDictDataList(dictData, null);
  }

  List<SysDictData> selectDictDataList(SysDictData dictData, IPage page);

  /**
   * 更新缓存中的字典信息
   *
   * @param dictType
   */
  void refreshDictData(String dictType);

  /**
   * 校验字典数据们是否有效。如下情况，视为无效： 1. 字典数据不存在 2. 字典数据被禁用
   *
   * @param dictType
   * @param values
   */
  void validateDictDataList(String dictType, Collection<String> values);

  /**
   * 同步修改字典类型
   *
   * @param oldDictType 旧字典类型
   * @param newDictType 新旧字典类型
   * @return 结果
   */
  int updateDictDataType(String oldDictType, String newDictType);

  /**
   * 获取同一Type下的字典配置
   *
   * @param dictType
   * @return
   */
  List<SysDictData> selectDictDataByType(String dictType);

  /**
   * 统计type下字典配置数量
   *
   * @param dictType
   * @return
   */
  long countDictDataByType(String dictType);
}
