package com.alphay.boot.system.mapper;

import com.alphay.boot.common.core.domain.entity.SysArea;
import com.alphay.boot.common.mybatis.mapper.BaseMapperX;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 地区管理Mapper接口
 *
 * @author d3code
 * @date 2024-06-21
 */
public interface SysAreaMapper extends BaseMapperX<SysArea> {

  /**
   * 通过节点ID查询所有子节点
   *
   * @return
   */
  List<SysArea> selectChildrenListByTreeId(Long id);

  @Update("truncate table sys_area")
  void truncate();

  List<SysArea> selectSysAreaList(IPage page, @Param("area") SysArea sysArea);
}
