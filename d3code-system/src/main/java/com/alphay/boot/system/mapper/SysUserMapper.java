package com.alphay.boot.system.mapper;

import java.util.List;
import java.util.Set;

import com.alphay.boot.common.core.domain.entity.SysUser;
import com.alphay.boot.common.mybatis.mapper.BaseMapperX;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

/**
 * 用户表 数据层
 *
 * @author d3code
 */
public interface SysUserMapper extends BaseMapperX<SysUser> {
  /**
   * 根据条件分页查询用户列表
   *
   * @param sysUser 用户信息
   * @param page 分页信息
   * @return 用户信息集合信息
   */
  List<SysUser> selectUserList(@Param("sysUser") SysUser sysUser, IPage page);

  /**
   * 根据条件分页查询已配用户角色列表
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
   * 修改用户头像
   *
   * @param userName 用户名
   * @param avatar 头像地址
   * @return 结果
   */
  int updateUserAvatar(@Param("userName") String userName, @Param("avatar") String avatar);

  /**
   * 重置用户密码
   *
   * @param userName 用户名
   * @param password 密码
   * @return 结果
   */
  int resetUserPwd(@Param("userName") String userName, @Param("password") String password);

  /**
   * 校验用户名称是否唯一
   *
   * @param userName 用户名称
   * @return 结果
   */
  SysUser checkUserNameUnique(String userName);

  /**
   * 校验手机号码是否唯一
   *
   * @param phonenumber 手机号码
   * @return 结果
   */
  SysUser checkPhoneUnique(String phonenumber);

  /**
   * 校验email是否唯一
   *
   * @param email 用户邮箱
   * @return 结果
   */
  SysUser checkEmailUnique(String email);

  /**
   * 通过部门获取用户
   *
   * @param deptIds
   * @return
   */
  default List<SysUser> selectListByDeptIds(Set<Long> deptIds) {
    return selectList(SysUser::getDeptId, deptIds);
  }

  default List<SysUser> selectListByPostIds(Set<Long> ids) {
    return selectList(SysUser::getPostIds, ids);
  }
}
