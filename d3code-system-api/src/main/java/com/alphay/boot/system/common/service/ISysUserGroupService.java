package com.alphay.boot.system.common.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.alphay.boot.system.common.domain.SysUserGroup;

import java.util.Collection;
import java.util.List;

/**
 * 用户分组接口
 *
 * @author Nottyjay
 */
public interface ISysUserGroupService extends IService<SysUserGroup> {

  /**
   * 获取用户组列表
   *
   * @param userGroup
   * @return
   */
  List<SysUserGroup> selectUserGroupList(SysUserGroup userGroup, IPage page);

  /**
   * 校验用户组们是否有效。如下情况，视为无效： 1. 用户组编号不存在 2. 用户组被禁用
   *
   * @param ids
   */
  void validUserGroups(Collection<Long> ids);

  /**
   * 获得用户组列表
   *
   * @param ids
   * @return
   */
  List<SysUserGroup> getUserGroupList(Collection<Long> ids);

  /**
   * 修改用户组状态
   *
   * @param userGroup
   * @return
   */
  boolean updateUserGroupStatus(SysUserGroup userGroup);

  /**
   * 获取授权的用户组
   *
   * @param userGroup
   * @param page
   * @return
   */
  List<SysUserGroup> selectAllocatedUserGroupList(SysUserGroup userGroup, IPage page);

  /**
   * 获取尚未授权的用户组
   *
   * @param userGroup
   * @param page
   * @return
   */
  List<SysUserGroup> selectUnallocatedUserGroupList(SysUserGroup userGroup, IPage page);
}
