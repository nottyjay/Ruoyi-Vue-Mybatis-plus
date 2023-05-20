package com.alphay.boot.system.service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.alphay.boot.common.core.domain.entity.SysUser;
import com.alphay.boot.common.core.domain.vo.SimpleUserVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 用户 业务层
 *
 * @author d3code
 */
public interface ISysUserService extends IService<SysUser> {
  /**
   * 根据条件分页查询用户列表
   *
   * @param user 用户信息
   * @return 用户信息集合信息
   */
  List<SysUser> selectUserList(SysUser user);

  /**
   * 根据条件分页查询已分配用户角色列表
   *
   * @param user 用户信息
   * @return 用户信息集合信息
   */
  List<SysUser> selectAllocatedList(SysUser user);

  /**
   * 根据条件分页查询未分配用户角色列表
   *
   * @param user 用户信息
   * @return 用户信息集合信息
   */
  List<SysUser> selectUnallocatedList(SysUser user);

  /**
   * 通过用户名查询用户
   *
   * @param userName 用户名
   * @return 用户对象信息
   */
  SysUser selectUserByUserName(String userName);

  /**
   * 通过用户ID查询用户
   *
   * @param userId 用户ID
   * @return 用户对象信息
   */
  SysUser selectUserById(Long userId);

  /**
   * 根据用户ID查询用户所属角色组
   *
   * @param userName 用户名
   * @return 结果
   */
  String selectUserRoleGroup(String userName);

  /**
   * 根据用户ID查询用户所属岗位组
   *
   * @param userName 用户名
   * @return 结果
   */
  String selectUserPostGroup(String userName);

  /**
   * 校验用户名称是否唯一
   *
   * @param user 用户信息
   * @return 结果
   */
  String checkUserNameUnique(SysUser user);

  /**
   * 校验手机号码是否唯一
   *
   * @param user 用户信息
   * @return 结果
   */
  String checkPhoneUnique(SysUser user);

  /**
   * 校验email是否唯一
   *
   * @param user 用户信息
   * @return 结果
   */
  String checkEmailUnique(SysUser user);

  /**
   * 校验用户是否允许操作
   *
   * @param user 用户信息
   */
  void checkUserAllowed(SysUser user);

  /**
   * 校验用户是否有数据权限
   *
   * @param userId 用户id
   */
  void checkUserDataScope(Long userId);

  /**
   * 注册用户信息
   *
   * @param user 用户信息
   * @return 结果
   */
  boolean registerUser(SysUser user);

  /**
   * 修改用户信息
   *
   * @param user 用户信息
   * @return 结果
   */
  int updateUser(SysUser user);

  /**
   * 用户授权角色
   *
   * @param userId 用户ID
   * @param roleIds 角色组
   */
  void insertUserAuth(Long userId, Long[] roleIds);

  /**
   * 修改用户状态
   *
   * @param user 用户信息
   * @return 结果
   */
  int updateUserStatus(SysUser user);

  /**
   * 修改用户基本信息
   *
   * @param user 用户信息
   * @return 结果
   */
  int updateUserProfile(SysUser user);

  /**
   * 修改用户头像
   *
   * @param userName 用户名
   * @param avatar 头像地址
   * @return 结果
   */
  boolean updateUserAvatar(String userName, String avatar);

  /**
   * 重置用户密码
   *
   * @param user 用户信息
   * @return 结果
   */
  int resetPwd(SysUser user);

  /**
   * 重置用户密码
   *
   * @param userName 用户名
   * @param password 密码
   * @return 结果
   */
  int resetUserPwd(String userName, String password);

  /**
   * 导入用户数据
   *
   * @param userList 用户数据列表
   * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
   * @param operName 操作用户
   * @return 结果
   */
  String importUser(List<SysUser> userList, Boolean isUpdateSupport, String operName);

  /**
   * 批量删除用户
   *
   * @param userIds
   * @return
   */
  boolean removeBatchByIds(List<Long> userIds);

  /**
   * 校验用户们是否有效。如下情况，视为无效： 1. 用户编号不存在 2. 用户被禁用
   *
   * @param ids
   */
  void validateUserList(Collection<Long> ids);

  /**
   * 获得用户列表
   *
   * @param ids
   * @return
   */
  List<SysUser> getUserList(Set<Long> ids);

  /**
   * 通过部门查询用户列表
   *
   * @param ids
   * @return
   */
  List<SysUser> getUserListByDeptIds(Set<Long> ids);

  /**
   * 通过岗位查询用户
   *
   * @param ids
   * @return
   */
  List<SysUser> getUsersByPostIds(Set<Long> ids);

  /**
   * 获取用户简单信息列表
   *
   * @param user
   * @return
   */
  List<SimpleUserVo> selectSimpleAllUserList(SysUser user);
}
