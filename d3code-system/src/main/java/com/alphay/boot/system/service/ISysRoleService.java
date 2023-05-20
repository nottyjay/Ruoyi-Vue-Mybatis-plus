package com.alphay.boot.system.service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.alphay.boot.common.core.domain.entity.SysRole;
import com.alphay.boot.system.domain.SysUserRole;

/**
 * 角色业务层
 *
 * @author d3code
 */
public interface ISysRoleService {
  /**
   * 根据条件分页查询角色数据
   *
   * @param role 角色信息
   * @return 角色数据集合信息
   */
  public List<SysRole> selectRoleList(SysRole role);

  /**
   * 根据用户ID查询角色列表
   *
   * @param userId 用户ID
   * @return 角色列表
   */
  public List<SysRole> selectRolesByUserId(Long userId);

  /**
   * 根据用户ID查询角色权限
   *
   * @param userId 用户ID
   * @return 权限列表
   */
  public Set<String> selectRolePermissionByUserId(Long userId);

  /**
   * 查询所有角色
   *
   * @return 角色列表
   */
  public List<SysRole> selectRoleAll();

  /**
   * 根据用户ID获取角色选择框列表
   *
   * @param userId 用户ID
   * @return 选中角色ID列表
   */
  public List<Long> selectRoleListByUserId(Long userId);

  /**
   * 通过角色ID查询角色
   *
   * @param roleId 角色ID
   * @return 角色对象信息
   */
  public SysRole selectRoleById(Long roleId);

  /**
   * 校验角色名称是否唯一
   *
   * @param role 角色信息
   * @return 结果
   */
  public String checkRoleNameUnique(SysRole role);

  /**
   * 校验角色权限是否唯一
   *
   * @param role 角色信息
   * @return 结果
   */
  public String checkRoleKeyUnique(SysRole role);

  /**
   * 校验角色是否允许操作
   *
   * @param role 角色信息
   */
  public void checkRoleAllowed(SysRole role);

  /**
   * 校验角色是否有数据权限
   *
   * @param roleId 角色id
   */
  public void checkRoleDataScope(Long roleId);

  /**
   * 通过角色ID查询角色使用数量
   *
   * @param roleId 角色ID
   * @return 结果
   */
  public int countUserRoleByRoleId(Long roleId);

  /**
   * 新增保存角色信息
   *
   * @param role 角色信息
   * @return 结果
   */
  public int insertRole(SysRole role);

  /**
   * 修改保存角色信息
   *
   * @param role 角色信息
   * @return 结果
   */
  public int updateRole(SysRole role);

  /**
   * 修改角色状态
   *
   * @param role 角色信息
   * @return 结果
   */
  public int updateRoleStatus(SysRole role);

  /**
   * 修改数据权限信息
   *
   * @param role 角色信息
   * @return 结果
   */
  public int authDataScope(SysRole role);

  /**
   * 通过角色ID删除角色
   *
   * @param roleId 角色ID
   * @return 结果
   */
  public int deleteRoleById(Long roleId);

  /**
   * 批量删除角色信息
   *
   * @param roleIds 需要删除的角色ID
   * @return 结果
   */
  public int deleteRoleByIds(Long[] roleIds);

  /**
   * 取消授权用户角色
   *
   * @param userRole 用户和角色关联信息
   * @return 结果
   */
  public int deleteAuthUser(SysUserRole userRole);

  /**
   * 批量取消授权用户角色
   *
   * @param roleId 角色ID
   * @param userIds 需要取消授权的用户数据ID
   * @return 结果
   */
  public int deleteAuthUsers(Long roleId, Long[] userIds);

  /**
   * 批量选择授权用户角色
   *
   * @param roleId 角色ID
   * @param userIds 需要删除的用户数据ID
   * @return 结果
   */
  public int insertAuthUsers(Long roleId, Long[] userIds);

  /**
   * 校验角色们是否有效。如下情况，视为无效： 1. 角色编号不存在 2. 角色被禁用
   *
   * @param ids
   */
  void validateRoleList(Collection<Long> ids);

  /**
   * 获取角色对应的用户列表
   *
   * @param ids
   * @return
   */
  List<SysUserRole> selectUserRoleListByRoleIds(Collection<Long> ids);
}
