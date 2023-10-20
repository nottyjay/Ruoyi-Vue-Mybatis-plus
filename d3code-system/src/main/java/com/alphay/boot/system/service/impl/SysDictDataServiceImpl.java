package com.alphay.boot.system.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import cn.hutool.core.collection.CollUtil;
import com.alphay.boot.common.core.domain.entity.SysDictData;
import com.alphay.boot.common.exception.ServiceException;
import com.alphay.boot.common.mybatis.query.LambdaQueryWrapperX;
import com.alphay.boot.common.mybatis.service.ServiceImplX;
import com.alphay.boot.common.utils.collection.CollectionUtil;
import com.alphay.boot.common.enums.SystemStatusEnum;
import com.alphay.boot.system.common.service.ISysDictDataService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
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
    return this.list(
        page,
        this.lambdaQueryWrapperX()
            .eqIfPresent(SysDictData::getDictType, dictData.getDictType())
            .likeIfPresent(SysDictData::getDictLabel, dictData.getDictLabel())
            .eqIfPresent(SysDictData::getStatus, dictData.getStatus())
            .orderByAsc(SysDictData::getDictSort));
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
    boolean result = super.updateById(data);
    if (result) {
      refreshDictData(data.getDictType());
    }
    return result;
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
    DictUtils.setDictCache(dictType, dictDatas);
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
}
