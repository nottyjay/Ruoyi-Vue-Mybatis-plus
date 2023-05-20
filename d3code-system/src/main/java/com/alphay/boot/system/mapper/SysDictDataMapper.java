package com.alphay.boot.system.mapper;

import java.util.Collection;
import java.util.List;

import com.alphay.boot.common.core.domain.entity.SysDictData;
import com.alphay.boot.common.mybatis.mapper.BaseMapperX;
import com.alphay.boot.common.mybatis.query.LambdaQueryWrapperX;
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
   * 根据字典类型查询字典数据
   *
   * @param dictType 字典类型
   * @return 字典数据集合信息
   */
  public List<SysDictData> selectDictDataByType(String dictType);

  /**
   * 根据字典类型和字典键值查询字典数据信息
   *
   * @param dictType 字典类型
   * @param dictValue 字典键值
   * @return 字典标签
   */
  public String selectDictLabel(
      @Param("dictType") String dictType, @Param("dictValue") String dictValue);

  /**
   * 根据字典数据ID查询信息
   *
   * @param dictCode 字典数据ID
   * @return 字典数据
   */
  public SysDictData selectDictDataById(Long dictCode);

  /**
   * 查询字典数据
   *
   * @param dictType 字典类型
   * @return 字典数据
   */
  public int countDictDataByType(String dictType);

  /**
   * 通过字典ID删除字典数据信息
   *
   * @param dictCode 字典数据ID
   * @return 结果
   */
  public int deleteDictDataById(Long dictCode);

  /**
   * 批量删除字典数据信息
   *
   * @param dictCodes 需要删除的字典数据ID
   * @return 结果
   */
  public int deleteDictDataByIds(Long[] dictCodes);

  /**
   * 新增字典数据信息
   *
   * @param dictData 字典数据信息
   * @return 结果
   */
  public int insertDictData(SysDictData dictData);

  /**
   * 修改字典数据信息
   *
   * @param dictData 字典数据信息
   * @return 结果
   */
  public int updateDictData(SysDictData dictData);

  /**
   * 同步修改字典类型
   *
   * @param oldDictType 旧字典类型
   * @param newDictType 新旧字典类型
   * @return 结果
   */
  public int updateDictDataType(
      @Param("oldDictType") String oldDictType, @Param("newDictType") String newDictType);

  default List<SysDictData> selectByDictTypeAndValues(String dictType, Collection<String> values) {
    return selectList(
        new LambdaQueryWrapperX<SysDictData>()
            .eq(SysDictData::getDictType, dictType)
            .in(SysDictData::getDictValue, values));
  }
}
