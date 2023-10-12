package com.alphay.boot.system.mapper;

import java.util.Collection;
import java.util.List;

import com.alphay.boot.common.mybatis.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Param;
import com.alphay.boot.system.common.domain.SysUserRole;

/**
 * 用户与角色关联表 数据层
 *
 * @author d3code
 */
public interface SysUserRoleMapper extends BaseMapperX<SysUserRole> {
  /**
   * 通过用户ID删除用户和角色关联
   *
   * @param userId 用户ID
   * @return 结果
   */
  public int deleteUserRoleByUserId(Long userId);

  /**
   * 批量删除用户和角色关联
   *
   * @param ids 需要删除的数据ID
   * @return 结果
   */
  public int deleteUserRole(Collection<Long> ids);

  /**
   * 通过角色ID查询角色使用数量
   *
   * @param roleId 角色ID
   * @return 结果
   */
  public int countUserRoleByRoleId(Long roleId);

  /**
   * 批量新增用户角色信息
   *
   * @param userRoleList 用户角色列表
   * @return 结果
   */
  public int batchUserRole(List<SysUserRole> userRoleList);

  /**
   * 删除用户和角色关联信息
   *
   * @param userRole 用户和角色关联信息
   * @return 结果
   */
  public int deleteUserRoleInfo(SysUserRole userRole);

  /**
   * 批量取消授权用户角色
   *
   * @param roleId 角色ID
   * @param userIds 需要删除的用户数据ID
   * @return 结果
   */
  public int deleteUserRoleInfos(@Param("roleId") Long roleId, @Param("userIds") Long[] userIds);

  /**
   * 获取角色组对应的用户列表
   *
   * @param ids
   * @return
   */
  default List<SysUserRole> selectUserRoleListByRoleIds(Collection<Long> ids) {
    return selectList(SysUserRole::getRoleId, ids);
  }
}
