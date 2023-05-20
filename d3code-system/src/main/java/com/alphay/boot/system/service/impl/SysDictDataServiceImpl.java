package com.alphay.boot.system.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import cn.hutool.core.collection.CollUtil;
import com.alphay.boot.common.core.domain.entity.SysDictData;
import com.alphay.boot.common.exception.ServiceException;
import com.alphay.boot.common.utils.collection.CollectionUtil;
import com.alphay.boot.common.enums.SystemStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alphay.boot.common.utils.DictUtils;
import com.alphay.boot.system.mapper.SysDictDataMapper;
import com.alphay.boot.system.service.ISysDictDataService;

/**
 * 字典 业务层处理
 *
 * @author d3code
 */
@Service
public class SysDictDataServiceImpl implements ISysDictDataService {
  @Autowired private SysDictDataMapper dictDataMapper;

  /**
   * 根据条件分页查询字典数据
   *
   * @param dictData 字典数据信息
   * @return 字典数据集合信息
   */
  @Override
  public List<SysDictData> selectDictDataList(SysDictData dictData) {
    return dictDataMapper.selectDictDataList(dictData);
  }

  /**
   * 根据字典类型和字典键值查询字典数据信息
   *
   * @param dictType 字典类型
   * @param dictValue 字典键值
   * @return 字典标签
   */
  @Override
  public String selectDictLabel(String dictType, String dictValue) {
    return dictDataMapper.selectDictLabel(dictType, dictValue);
  }

  /**
   * 根据字典数据ID查询信息
   *
   * @param dictCode 字典数据ID
   * @return 字典数据
   */
  @Override
  public SysDictData selectDictDataById(Long dictCode) {
    return dictDataMapper.selectDictDataById(dictCode);
  }

  /**
   * 批量删除字典数据信息
   *
   * @param dictCodes 需要删除的字典数据ID
   */
  @Override
  public void deleteDictDataByIds(Long[] dictCodes) {
    for (Long dictCode : dictCodes) {
      SysDictData data = selectDictDataById(dictCode);
      dictDataMapper.deleteDictDataById(dictCode);
      List<SysDictData> dictDatas = dictDataMapper.selectDictDataByType(data.getDictType());
      DictUtils.setDictCache(data.getDictType(), dictDatas);
    }
  }

  /**
   * 新增保存字典数据信息
   *
   * @param data 字典数据信息
   * @return 结果
   */
  @Override
  public int insertDictData(SysDictData data) {
    int row = dictDataMapper.insertDictData(data);
    if (row > 0) {
      List<SysDictData> dictDatas = dictDataMapper.selectDictDataByType(data.getDictType());
      DictUtils.setDictCache(data.getDictType(), dictDatas);
    }
    return row;
  }

  /**
   * 修改保存字典数据信息
   *
   * @param data 字典数据信息
   * @return 结果
   */
  @Override
  public int updateDictData(SysDictData data) {
    int row = dictDataMapper.updateDictData(data);
    if (row > 0) {
      List<SysDictData> dictDatas = dictDataMapper.selectDictDataByType(data.getDictType());
      DictUtils.setDictCache(data.getDictType(), dictDatas);
    }
    return row;
  }

  @Override
  public void validateDictDataList(String dictType, Collection<String> values) {
    if (CollUtil.isEmpty(values)) {
      return;
    }

    List<SysDictData> dictDataList = dictDataMapper.selectByDictTypeAndValues(dictType, values);
    Map<String, SysDictData> dictDataMap =
        CollectionUtil.convertMap(dictDataList, SysDictData::getDictValue);
    // 校验
    values.forEach(
        value -> {
          SysDictData dictData = dictDataMap.get(value);
          if (dictData == null) {
            throw new ServiceException("当前字典数据不存在");
          }
          if (!SystemStatusEnum.ENABLE.getStatus().equals(dictData.getStatus())) {
            throw new ServiceException("字典数据(" + dictData.getDictLabel() + ")不处于开启状态，不允许选择");
          }
        });
  }
}
