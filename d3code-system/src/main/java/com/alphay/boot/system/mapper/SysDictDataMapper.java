package com.alphay.boot.system.mapper;

import java.util.Collection;
import java.util.List;

import com.alphay.boot.common.core.domain.entity.SysDictData;
import com.alphay.boot.common.mybatis.mapper.BaseMapperX;
import com.alphay.boot.common.mybatis.query.LambdaQueryWrapperX;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.apache.ibatis.annotations.Param;

/**
 * 字典表 数据层
 *
 * @author d3code
 */
public interface SysDictDataMapper extends BaseMapperX<SysDictData> {
  /**
   * 根据条件分页查询字典数据
   *
   * @param dictData 字典数据信息
   * @return 字典数据集合信息
   */
  public List<SysDictData> selectDictDataList(SysDictData dictData);

  /**
   * 同步修改字典类型
   *
   * @param oldDictType 旧字典类型
   * @param newDictType 新旧字典类型
   * @return 结果
   */
  default int updateDictDataType(String oldDictType, String newDictType) {
    return this.update(
        null,
        new UpdateWrapper<SysDictData>()
            .set("dict_type", newDictType)
            .eq("dict_type", oldDictType));
  }

  default List<SysDictData> selectByDictTypeAndValues(String dictType, Collection<String> values) {
    return selectList(
        new LambdaQueryWrapperX<SysDictData>()
            .eq(SysDictData::getDictType, dictType)
            .in(SysDictData::getDictValue, values));
  }
}
