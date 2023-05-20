package com.alphay.boot.system.api;

import com.alphay.boot.common.core.domain.entity.SysUser;
import com.alphay.boot.common.utils.collection.CollectionUtil;
import com.alphay.boot.system.service.ISysUserService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 系统管理员 Api
 *
 * @author Nottyjay
 */
@Component
public class AdminApi {

  @Resource private ISysUserService userService;

  /**
   * 校验用户们是否有效。如下情况，视为无效： 1. 用户编号不存在 2. 用户被禁用
   *
   * @param ids
   */
  public void validateUserList(Collection<Long> ids) {
    userService.validateUserList(ids);
  }

  /**
   * 通过用户 ID 查询用户
   *
   * @param id
   * @return
   */
  public SysUser getUser(long id) {
    return userService.selectUserById(id);
  }

  /**
   * 获得用户 Map
   *
   * @param ids
   * @return
   */
  public Map<Long, SysUser> getUserMap(Set<Long> ids) {
    List<SysUser> userList = userService.getUserList(ids);
    return CollectionUtil.convertMap(userList, SysUser::getUserId);
  }

  /**
   * 通过部门查询用户
   *
   * @param ids
   * @return
   */
  public List<SysUser> getUserListByDeptIds(Set<Long> ids) {
    return userService.getUserListByDeptIds(ids);
  }

  /**
   * 通过岗位查询用户
   *
   * @param ids
   * @return
   */
  public List<SysUser> getUsersByPostIds(Set<Long> ids) {
    return userService.getUsersByPostIds(ids);
  }
}
