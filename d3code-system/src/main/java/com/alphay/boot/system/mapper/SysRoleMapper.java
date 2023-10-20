package com.alphay.boot.system.mapper;

import java.util.List;

import com.alphay.boot.common.core.domain.entity.SysRole;
import com.alphay.boot.common.mybatis.mapper.BaseMapperX;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

/**
 * 角色表 数据层
 *
 * @author d3code
 */
public interface SysRoleMapper extends BaseMapperX<SysRole> {
  /**
   * 根据条件分页查询角色数据
   *
   * @param role 角色信息
   * @param page 分页信息
   * @return 角色数据集合信息
   */
  List<SysRole> selectRoleList(@Param("role") SysRole role, IPage page);

  /**
   * 根据用户ID查询角色
   *
   * @param userId 用户ID
   * @return 角色列表
   */
  List<SysRole> selectRolePermissionByUserId(Long userId);

  /**
   * 查询所有角色
   *
   * @return 角色列表
   */
  List<SysRole> selectRoleAll();

  /**
   * 根据用户ID获取角色选择框列表
   *
   * @param userId 用户ID
   * @return 选中角色ID列表
   */
  List<Long> selectRoleListByUserId(Long userId);

  /**
   * 通过角色ID查询角色
   *
   * @param roleId 角色ID
   * @return 角色对象信息
   */
  SysRole selectRoleById(Long roleId);

  /**
   * 根据用户ID查询角色
   *
   * @param userName 用户名
   * @return 角色列表
   */
  List<SysRole> selectRolesByUserName(String userName);

  /**
   * 校验角色名称是否唯一
   *
   * @param roleName 角色名称
   * @return 角色信息
   */
  SysRole checkRoleNameUnique(String roleName);

  /**
   * 校验角色权限是否唯一
   *
   * @param roleKey 角色权限
   * @return 角色信息
   */
  SysRole checkRoleKeyUnique(String roleKey);

  /**
   * 新增角色信息
   *
   * @param role 角色信息
   * @return 结果
   */
  int insertRole(SysRole role);

  /**
   * 通过角色ID删除角色
   *
   * @param roleId 角色ID
   * @return 结果
   */
  int deleteRoleById(Long roleId);

  /**
   * 批量删除角色信息
   *
   * @param roleIds 需要删除的角色ID
   * @return 结果
   */
  int deleteRoleByIds(Long[] roleIds);
}
