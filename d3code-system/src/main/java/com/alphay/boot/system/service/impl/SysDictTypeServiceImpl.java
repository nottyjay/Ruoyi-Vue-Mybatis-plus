package com.alphay.boot.system.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.alphay.boot.common.core.domain.entity.SysDictData;
import com.alphay.boot.common.core.domain.entity.SysDictType;
import com.alphay.boot.common.exception.ServiceException;
import com.alphay.boot.common.mybatis.query.QueryWrapperX;
import com.alphay.boot.common.mybatis.service.ServiceImplX;
import com.alphay.boot.system.common.service.ISysDictDataService;
import com.alphay.boot.system.common.service.ISysDictTypeService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.alphay.boot.common.constant.UserConstants;
import com.alphay.boot.common.utils.DictUtils;
import com.alphay.boot.common.utils.StringUtils;
import com.alphay.boot.system.mapper.SysDictDataMapper;
import com.alphay.boot.system.mapper.SysDictTypeMapper;

/**
 * 字典 业务层处理
 *
 * @author d3code
 */
@Service
public class SysDictTypeServiceImpl extends ServiceImplX<SysDictTypeMapper, SysDictType>
    implements ISysDictTypeService {
  @Autowired private ISysDictDataService dictDataService;

  /**
   * 根据条件分页查询字典类型
   *
   * @param dictType 字典类型信息
   * @return 字典类型集合信息
   */
  @Override
  public List<SysDictType> selectDictTypeList(SysDictType dictType, IPage page) {
    QueryWrapperX<SysDictType> queryWrapper =
        new QueryWrapperX<SysDictType>()
            .likeIfPresent("dict_name", dictType.getDictName())
            .eqIfPresent("status", dictType.getStatus())
            .likeIfPresent("dict_type", dictType.getDictType());
    parseBeginTimeAndEndTime(dictType.getParams(), queryWrapper, "create_time");
    return this.list(page, queryWrapper);
  }

  /**
   * 根据所有字典类型
   *
   * @return 字典类型集合信息
   */
  @Override
  public List<SysDictType> selectDictTypeAll() {
    return this.list();
  }

  /**
   * 根据字典类型查询字典数据
   *
   * @param dictType 字典类型
   * @return 字典数据集合信息
   */
  @Override
  public List<SysDictData> selectDictDataByType(String dictType) {
    List<SysDictData> dictDatas = DictUtils.getDictCache(dictType);
    if (StringUtils.isNotEmpty(dictDatas)) {
      return dictDatas;
    }
    dictDataService.refreshDictData(dictType);
    dictDatas = DictUtils.getDictCache(dictType);
    if (StringUtils.isNotEmpty(dictDatas)) {
      return dictDatas;
    }
    return null;
  }

  /**
   * 根据字典类型查询信息
   *
   * @param dictType 字典类型
   * @return 字典类型
   */
  @Override
  public SysDictType getOneByType(String dictType) {
    return this.getOne(this.lambdaQueryWrapperX().eq(SysDictType::getDictType, dictType));
  }

  /**
   * 批量删除字典类型信息
   *
   * @param dictIds 需要删除的字典ID
   */
  @Override
  public void deleteDictTypeByIds(Long[] dictIds) {
    for (Long dictId : dictIds) {
      SysDictType dictType = getById(dictId);
      if (dictDataService.countDictDataByType(dictType.getDictType()) > 0) {
        throw new ServiceException(String.format("%1$s已分配,不能删除", dictType.getDictName()));
      }
      this.removeById(dictId);
      DictUtils.removeDictCache(dictType.getDictType());
    }
  }

  /** 加载字典缓存数据 */
  @Override
  public void loadingDictCache() {
    SysDictData dictData = new SysDictData();
    dictData.setStatus("0");
    Map<String, List<SysDictData>> dictDataMap =
        dictDataService.selectDictDataList(dictData).stream()
            .collect(Collectors.groupingBy(SysDictData::getDictType));
    for (Map.Entry<String, List<SysDictData>> entry : dictDataMap.entrySet()) {
      DictUtils.setDictCache(
          entry.getKey(),
          entry.getValue().stream()
              .sorted(Comparator.comparing(SysDictData::getDictSort))
              .collect(Collectors.toList()));
    }
  }

  /** 清空字典缓存数据 */
  @Override
  public void clearDictCache() {
    DictUtils.clearDictCache();
  }

  /** 重置字典缓存数据 */
  @Override
  public void resetDictCache() {
    clearDictCache();
    loadingDictCache();
  }

  /**
   * 新增保存字典类型信息
   *
   * @param dict 字典类型信息
   * @return 结果
   */
  @Override
  public boolean save(SysDictType dict) {
    boolean result = super.save(dict);
    if (result) {
      DictUtils.setDictCache(dict.getDictType(), null);
    }
    return result;
  }

  /**
   * 修改保存字典类型信息
   *
   * @param dict 字典类型信息
   * @return 结果
   */
  @Override
  @Transactional
  public boolean updateById(SysDictType dict) {
    SysDictType oldDict = this.getById(dict.getDictId());
    dictDataService.updateDictDataType(oldDict.getDictType(), dict.getDictType());
    boolean result = super.updateById(dict);
    if (result) {
      dictDataService.refreshDictData(dict.getDictType());
    }
    return result;
  }

  /**
   * 校验字典类型称是否唯一
   *
   * @param dict 字典类型
   * @return 结果
   */
  @Override
  public String checkDictTypeUnique(SysDictType dict) {
    Long dictId = StringUtils.isNull(dict.getDictId()) ? -1L : dict.getDictId();
    SysDictType dictType = this.baseMapper.checkDictTypeUnique(dict.getDictType());
    if (StringUtils.isNotNull(dictType) && dictType.getDictId().longValue() != dictId.longValue()) {
      return UserConstants.NOT_UNIQUE;
    }
    return UserConstants.UNIQUE;
  }
}
