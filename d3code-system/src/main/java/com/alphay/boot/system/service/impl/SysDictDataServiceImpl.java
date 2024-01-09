package com.alphay.boot.system.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import com.alphay.boot.common.constant.UserConstants;
import com.alphay.boot.common.core.domain.entity.SysDictData;
import com.alphay.boot.common.core.text.Convert;
import com.alphay.boot.common.exception.ServiceException;
import com.alphay.boot.common.mybatis.service.ServiceImplX;
import com.alphay.boot.common.utils.StringUtils;
import com.alphay.boot.common.utils.collection.CollectionUtil;
import com.alphay.boot.common.enums.SystemStatusEnum;
import com.alphay.boot.system.common.service.ISysDictDataService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;
import com.alphay.boot.common.utils.DictUtils;
import com.alphay.boot.system.mapper.SysDictDataMapper;

/**
 * 字典 业务层处理
 *
 * @author d3code
 */
@Service
public class SysDictDataServiceImpl extends ServiceImplX<SysDictDataMapper, SysDictData>
    implements ISysDictDataService {

  /**
   * 根据条件分页查询字典数据
   *
   * @param dictData 字典数据信息
   * @return 字典数据集合信息
   */
  @Override
  public List<SysDictData> selectDictDataList(SysDictData dictData, IPage page) {
    LambdaQueryWrapper<SysDictData> query =
        this.lambdaQueryWrapperX()
            .eqIfPresent(SysDictData::getDictType, dictData.getDictType())
            .likeIfPresent(SysDictData::getDictLabel, dictData.getDictLabel())
            .eqIfPresent(SysDictData::getStatus, dictData.getStatus())
            .orderByAsc(SysDictData::getDictSort);
    if (dictData.getParentDictCode() != null) {
      query.eq(SysDictData::getParentDictCode, dictData.getParentDictCode());
    } else {
      query.isNull(SysDictData::getParentDictCode);
    }
    return this.list(page, query);
  }

  /**
   * 同步修改字典类型
   *
   * @param oldDictType 旧字典类型
   * @param newDictType 新旧字典类型
   * @return 结果
   */
  @Override
  public int updateDictDataType(String oldDictType, String newDictType) {
    return this.baseMapper.updateDictDataType(oldDictType, newDictType);
  }

  /**
   * 获取同一Type下的字典配置
   *
   * @param dictType
   * @return
   */
  @Override
  public List<SysDictData> selectDictDataByType(String dictType) {
    return this.list(
        this.lambdaQueryWrapperX()
            .eq(SysDictData::getStatus, "0")
            .eq(SysDictData::getDictType, dictType)
            .orderByAsc(SysDictData::getDictSort));
  }

  /**
   * 统计type下字典配置数量
   *
   * @param dictType
   * @return
   */
  @Override
  public long countDictDataByType(String dictType) {
    return this.count(this.lambdaQueryWrapperX().eq(SysDictData::getDictType, dictType));
  }

  /**
   * 批量删除字典数据信息
   *
   * @param dictCodes 需要删除的字典数据ID
   */
  @Override
  public boolean removeByIds(Collection<?> dictCodes) {
    for (Object dictCode : dictCodes) {
      SysDictData data = this.getById((Long) dictCode);
      this.removeById((Long) dictCode);
      refreshDictData(data.getDictType());
    }
    return true;
  }

  /**
   * 新增保存字典数据信息
   *
   * @param data 字典数据信息
   * @return 结果
   */
  @Override
  public boolean save(SysDictData data) {
    if (data.getParentDictCode() != null) {
      SysDictData info = this.getById(data.getParentDictCode());
      if (ObjUtil.isNull(info)) {
        throw new ServiceException("找不到对应的上级字典");
      }
      // 如果父节点不为正常状态，则不允许新增子节点
      if (!UserConstants.DICT_NORMAL.equals(info.getStatus())) {
        throw new ServiceException("字段已停用，不允许新增");
      }
      data.setFullPathDictCode(info.getFullPathDictCode() + "," + data.getParentDictCode());
      data.setFullPathDictValue(info.getFullPathDictValue() + "," + data.getDictValue());
    } else {
      data.setFullPathDictCode("0");
      data.setFullPathDictValue(data.getDictValue());
    }
    boolean result = super.save(data);
    if (result) {
      refreshDictData(data.getDictType());
    }
    return result;
  }

  /**
   * 修改保存字典数据信息
   *
   * @param data 字典数据信息
   * @return 结果
   */
  @Override
  public boolean updateById(SysDictData data) {
    SysDictData newParentData = this.getById(data.getParentDictCode());
    SysDictData oldPersistent = this.getById(data.getDictCode());
    if (ObjUtil.isNotNull(newParentData) && ObjUtil.isNotNull(oldPersistent)) {
      String newFullPathCode =
          newParentData.getFullPathDictCode() + "," + newParentData.getDictCode();
      String oldFullPathCode = oldPersistent.getFullPathDictCode();
      data.setFullPathDictCode(newFullPathCode);

      String newFullPathValue = newParentData.getFullPathDictValue() + "," + data.getDictValue();
      String oldFullPathValue = oldPersistent.getFullPathDictValue();
      data.setFullPathDictValue(newFullPathValue);
      this.updateDictDataChildren(
          data.getDictCode(), newFullPathCode, oldFullPathCode, newFullPathValue, oldFullPathValue);
    }
    boolean result = super.updateById(data);
    if (result) {
      if (UserConstants.DICT_NORMAL.equals(data.getStatus())
          && StringUtils.isNotEmpty(data.getFullPathDictCode())
          && !StringUtils.equals("0", data.getFullPathDictCode())) {
        this.updateParentDictDataStatusNormal(data);
      }
      refreshDictData(data.getDictType());
    }
    return result;
  }

  private void updateParentDictDataStatusNormal(SysDictData data) {
    String ancestors = data.getFullPathDictCode();
    Long[] dataCodes = Convert.toLongArray(ancestors);
    this.update(
        Wrappers.<SysDictData>lambdaUpdate()
            .set(SysDictData::getStatus, "0")
            .in(SysDictData::getDictCode, dataCodes));
  }

  @Override
  public boolean update(Wrapper<SysDictData> updateWrapper) {
    return super.update(updateWrapper);
  }

  /**
   * 更新缓存中的字典信息
   *
   * @param dictType
   */
  @Override
  public void refreshDictData(String dictType) {
    List<SysDictData> dictDatas = selectDictDataByType(dictType);
    // TODO 整理DictData信息
    dictDatas = evalChildrenDictDatas(dictDatas);
    DictUtils.setDictCache(dictType, dictDatas);
  }

  private List<SysDictData> evalChildrenDictDatas(List<SysDictData> dictDatas) {
    boolean hasChildren = dictDatas.stream().anyMatch(item -> item.getParentDictCode() != null);
    if (hasChildren) {
      for (int index = 0; index < dictDatas.size(); index++) {
        SysDictData data = dictDatas.get(index);
        recursionFn(dictDatas, data);
      }
    }
    return dictDatas;
  }

  private List<SysDictData> recursionFn(List<SysDictData> list, SysDictData dictData) {
    if (CollUtil.isEmpty(list)) {
      return list;
    }
    for (int index = 0; index < list.size(); index++) {
      SysDictData data = list.get(index);
      //    for (SysDictData data : list) {
      if (data.getParentDictCode() != null
          && dictData.getDictCode().longValue() == data.getParentDictCode().longValue()) {
        if (CollUtil.isEmpty(dictData.getChildren())) {
          dictData.setChildren(new ArrayList<>());
        }
        dictData.getChildren().add(data);
        list.remove(data);
        recursionFn(list, data);
      }
    }
    return list;
  }

  @Override
  public void validateDictDataList(String dictType, Collection<String> values) {
    if (CollUtil.isEmpty(values)) {
      return;
    }

    List<SysDictData> dictDataList = this.baseMapper.selectByDictTypeAndValues(dictType, values);
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

  private void updateDictDataChildren(
      Long dictCode,
      String newFullPathCode,
      String oldFullPathCode,
      String newFullPathValue,
      String oldFullPathValue) {
    List<SysDictData> children = baseMapper.selectDictDataChildrenListByCode(dictCode);
    if (CollUtil.isNotEmpty(children)) {
      for (SysDictData child : children) {
        child.setFullPathDictCode(
            child.getFullPathDictCode().replaceFirst(oldFullPathCode, newFullPathCode));
        child.setFullPathDictValue(
            child.getFullPathDictValue().replaceFirst(oldFullPathValue, newFullPathValue));
      }
      this.updateBatchById(children);
    }
  }
}
