package com.alphay.boot.system.api;

import com.alphay.boot.common.utils.collection.CollectionUtil;
import com.alphay.boot.system.service.ISysRoleService;
import com.alphay.boot.system.domain.SysUserRole;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Set;

/**
 * 角色 API
 *
 * @author Nottyjay
 */
@Component
public class RoleApi {

  @Resource private ISysRoleService roleService;

  /**
   * 校验角色们是否有效。如下情况，视为无效： 1. 角色编号不存在 2. 角色被禁用
   *
   * @param ids 角色编号数组
   */
  public void validRoleList(Collection<Long> ids) {
    roleService.validateRoleList(ids);
  }

  /**
   * 获取角色对应的用户列表
   *
   * @param ids
   * @return
   */
  public Set<Long> getUserRoleIdListByRoleIds(Set<Long> ids) {
    return CollectionUtil.convertSet(
        roleService.selectUserRoleListByRoleIds(ids), SysUserRole::getUserId);
  }
}
