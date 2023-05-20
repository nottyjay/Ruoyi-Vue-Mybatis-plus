package com.alphay.boot.system.service;

import java.util.Collection;
import java.util.List;

import com.alphay.boot.common.core.domain.TreeSelect;
import com.alphay.boot.common.core.domain.entity.SysDept;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 部门管理 服务层
 *
 * @author d3code
 */
public interface ISysDeptService extends IService<SysDept> {
  /**
   * 查询部门管理数据
   *
   * @param dept 部门信息
   * @return 部门信息集合
   */
  public List<SysDept> selectDeptList(SysDept dept);

  /**
   * 查询部门树结构信息
   *
   * @param dept 部门信息
   * @return 部门树信息集合
   */
  public List<TreeSelect> selectDeptTreeList(SysDept dept);

  /**
   * 构建前端所需要树结构
   *
   * @param depts 部门列表
   * @return 树结构列表
   */
  public List<SysDept> buildDeptTree(List<SysDept> depts);

  /**
   * 构建前端所需要下拉树结构
   *
   * @param depts 部门列表
   * @return 下拉树结构列表
   */
  public List<TreeSelect> buildDeptTreeSelect(List<SysDept> depts);

  /**
   * 根据角色ID查询部门树信息
   *
   * @param roleId 角色ID
   * @return 选中部门列表
   */
  public List<Long> selectDeptListByRoleId(Long roleId);

  /**
   * 根据部门ID查询信息
   *
   * @param deptId 部门ID
   * @return 部门信息
   */
  public SysDept selectDeptById(Long deptId);

  /**
   * 根据ID查询所有子部门（正常状态）
   *
   * @param deptId 部门ID
   * @return 子部门数
   */
  public int selectNormalChildrenDeptById(Long deptId);

  /**
   * 是否存在部门子节点
   *
   * @param deptId 部门ID
   * @return 结果
   */
  public boolean hasChildByDeptId(Long deptId);

  /**
   * 查询部门是否存在用户
   *
   * @param deptId 部门ID
   * @return 结果 true 存在 false 不存在
   */
  public boolean checkDeptExistUser(Long deptId);

  /**
   * 校验部门名称是否唯一
   *
   * @param dept 部门信息
   * @return 结果
   */
  public String checkDeptNameUnique(SysDept dept);

  /**
   * 校验部门是否有数据权限
   *
   * @param deptId 部门id
   */
  public void checkDeptDataScope(Long deptId);

  /**
   * 新增保存部门信息
   *
   * @param dept 部门信息
   * @return 结果
   */
  public boolean insertDept(SysDept dept);

  /**
   * 修改保存部门信息
   *
   * @param dept 部门信息
   * @return 结果
   */
  public int updateDept(SysDept dept);

  /**
   * 校验部门们是否有效。如下情况，视为无效： 1. 部门编号不存在 2. 部门被禁用
   *
   * @param ids
   */
  void validateDeptList(Collection<Long> ids);

  /**
   * 获得部门信息数组
   *
   * @param ids
   * @return
   */
  List<SysDept> getDeptList(Collection<Long> ids);
}
