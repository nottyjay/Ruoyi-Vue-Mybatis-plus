package com.alphay.boot.system.service.impl;

import java.util.*;
import java.util.stream.Collectors;
import javax.validation.Validator;

import cn.hutool.core.collection.CollUtil;
import com.alphay.boot.common.annotation.DataScope;
import com.alphay.boot.common.core.convert.SysUserConvert;
import com.alphay.boot.common.core.domain.entity.SysRole;
import com.alphay.boot.common.core.domain.entity.SysUser;
import com.alphay.boot.common.core.domain.vo.SimpleUserVo;
import com.alphay.boot.common.exception.ServiceException;
import com.alphay.boot.common.mybatis.query.LambdaQueryWrapperX;
import com.alphay.boot.common.utils.bean.BeanValidators;
import com.alphay.boot.common.utils.collection.CollectionUtil;
import com.alphay.boot.common.utils.spring.SpringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.alphay.boot.common.enums.SystemStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import com.alphay.boot.common.constant.UserConstants;
import com.alphay.boot.common.utils.SecurityUtils;
import com.alphay.boot.common.utils.StringUtils;
import com.alphay.boot.system.common.domain.SysPost;
import com.alphay.boot.system.common.domain.SysUserPost;
import com.alphay.boot.system.common.domain.SysUserRole;
import com.alphay.boot.system.mapper.SysPostMapper;
import com.alphay.boot.system.mapper.SysRoleMapper;
import com.alphay.boot.system.mapper.SysUserMapper;
import com.alphay.boot.system.mapper.SysUserPostMapper;
import com.alphay.boot.system.mapper.SysUserRoleMapper;
import com.alphay.boot.system.common.service.ISysConfigService;
import com.alphay.boot.system.common.service.ISysUserService;

/**
 * 用户 业务层处理
 *
 * @author d3code
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
    implements ISysUserService {
  private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);

  @Autowired private SysUserMapper userMapper;

  @Autowired private SysRoleMapper roleMapper;

  @Autowired private SysPostMapper postMapper;

  @Autowired private SysUserRoleMapper userRoleMapper;

  @Autowired private SysUserPostMapper userPostMapper;

  @Autowired private ISysConfigService configService;

  @Autowired protected Validator validator;

  /**
   * 根据条件分页查询用户列表
   *
   * @param user 用户信息
   * @return 用户信息集合信息
   */
  @Override
  @DataScope(deptAlias = "d", userAlias = "u")
  public List<SysUser> selectUserList(SysUser user) {
    return userMapper.selectUserList(user);
  }

  @Override
  public List<SimpleUserVo> selectSimpleAllUserList(SysUser user) {
    List<SysUser> result =
        baseMapper.selectList(
            new LambdaQueryWrapperX<SysUser>()
                .eqIfPresent(SysUser::getDeptId, user.getDeptId())
                .select(SysUser::getUserId, SysUser::getDeptId, SysUser::getNickName));
    return SysUserConvert.INSTANCE.convertList(result);
  }

  /**
   * 根据条件分页查询已分配用户角色列表
   *
   * @param user 用户信息
   * @return 用户信息集合信息
   */
  @Override
  @DataScope(deptAlias = "d", userAlias = "u")
  public List<SysUser> selectAllocatedList(SysUser user) {
    return userMapper.selectAllocatedList(user);
  }

  /**
   * 根据条件分页查询未分配用户角色列表
   *
   * @param user 用户信息
   * @return 用户信息集合信息
   */
  @Override
  @DataScope(deptAlias = "d", userAlias = "u")
  public List<SysUser> selectUnallocatedList(SysUser user) {
    return userMapper.selectUnallocatedList(user);
  }

  /**
   * 通过用户名查询用户
   *
   * @param userName 用户名
   * @return 用户对象信息
   */
  @Override
  public SysUser selectUserByUserName(String userName) {
    return userMapper.selectUserByUserName(userName);
  }

  /**
   * 通过用户ID查询用户
   *
   * @param userId 用户ID
   * @return 用户对象信息
   */
  @Override
  public SysUser selectUserById(Long userId) {
    return userMapper.selectUserById(userId);
  }

  /**
   * 查询用户所属角色组
   *
   * @param userName 用户名
   * @return 结果
   */
  @Override
  public String selectUserRoleGroup(String userName) {
    List<SysRole> list = roleMapper.selectRolesByUserName(userName);
    if (CollectionUtils.isEmpty(list)) {
      return StringUtils.EMPTY;
    }
    return list.stream().map(SysRole::getRoleName).collect(Collectors.joining(","));
  }

  /**
   * 查询用户所属岗位组
   *
   * @param userName 用户名
   * @return 结果
   */
  @Override
  public String selectUserPostGroup(String userName) {
    List<SysPost> list = postMapper.selectPostsByUserName(userName);
    if (CollectionUtils.isEmpty(list)) {
      return StringUtils.EMPTY;
    }
    return list.stream().map(SysPost::getPostName).collect(Collectors.joining(","));
  }

  /**
   * 校验用户名称是否唯一
   *
   * @param user 用户信息
   * @return 结果
   */
  @Override
  public String checkUserNameUnique(SysUser user) {
    Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
    SysUser info = userMapper.checkUserNameUnique(user.getUserName());
    if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue()) {
      return UserConstants.NOT_UNIQUE;
    }
    return UserConstants.UNIQUE;
  }

  /**
   * 校验手机号码是否唯一
   *
   * @param user 用户信息
   * @return
   */
  @Override
  public String checkPhoneUnique(SysUser user) {
    Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
    SysUser info = userMapper.checkPhoneUnique(user.getPhonenumber());
    if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue()) {
      return UserConstants.NOT_UNIQUE;
    }
    return UserConstants.UNIQUE;
  }

  /**
   * 校验email是否唯一
   *
   * @param user 用户信息
   * @return
   */
  @Override
  public String checkEmailUnique(SysUser user) {
    Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
    SysUser info = userMapper.checkEmailUnique(user.getEmail());
    if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue()) {
      return UserConstants.NOT_UNIQUE;
    }
    return UserConstants.UNIQUE;
  }

  /**
   * 校验用户是否允许操作
   *
   * @param user 用户信息
   */
  @Override
  public void checkUserAllowed(SysUser user) {
    if (StringUtils.isNotNull(user.getUserId()) && user.isAdmin()) {
      throw new ServiceException("不允许操作超级管理员用户");
    }
  }

  /**
   * 校验用户是否有数据权限
   *
   * @param userId 用户id
   */
  @Override
  public void checkUserDataScope(Long userId) {
    if (!SysUser.isAdmin(SecurityUtils.getUserId())) {
      SysUser user = new SysUser();
      user.setUserId(userId);
      List<SysUser> users = SpringUtils.getAopProxy(this).selectUserList(user);
      if (StringUtils.isEmpty(users)) {
        throw new ServiceException("没有权限访问用户数据！");
      }
    }
  }

  /**
   * 新增保存用户信息
   *
   * @param user 用户信息
   * @return 结果
   */
  @Override
  @Transactional
  public boolean save(SysUser user) {
    // 新增用户信息
    boolean result = super.save(user);
    // 新增用户岗位关联
    insertUserPost(user);
    // 新增用户与角色管理
    insertUserRole(user);
    return result;
  }

  /**
   * 注册用户信息
   *
   * @param user 用户信息
   * @return 结果
   */
  @Override
  public boolean registerUser(SysUser user) {
    return this.save(user);
  }

  /**
   * 修改保存用户信息
   *
   * @param user 用户信息
   * @return 结果
   */
  @Override
  @Transactional
  public int updateUser(SysUser user) {
    Long userId = user.getUserId();
    // 删除用户与角色关联
    userRoleMapper.deleteUserRoleByUserId(userId);
    // 新增用户与角色管理
    insertUserRole(user);
    // 删除用户与岗位关联
    userPostMapper.deleteUserPostByUserId(userId);
    // 新增用户与岗位管理
    insertUserPost(user);
    return userMapper.updateById(user);
  }

  /**
   * 用户授权角色
   *
   * @param userId 用户ID
   * @param roleIds 角色组
   */
  @Override
  @Transactional
  public void insertUserAuth(Long userId, Long[] roleIds) {
    userRoleMapper.deleteUserRoleByUserId(userId);
    insertUserRole(userId, roleIds);
  }

  /**
   * 修改用户状态
   *
   * @param user 用户信息
   * @return 结果
   */
  @Override
  public int updateUserStatus(SysUser user) {
    return userMapper.updateById(user);
  }

  /**
   * 修改用户基本信息
   *
   * @param user 用户信息
   * @return 结果
   */
  @Override
  public int updateUserProfile(SysUser user) {
    return userMapper.updateById(user);
  }

  /**
   * 修改用户头像
   *
   * @param userName 用户名
   * @param avatar 头像地址
   * @return 结果
   */
  @Override
  public boolean updateUserAvatar(String userName, String avatar) {
    return userMapper.updateUserAvatar(userName, avatar) > 0;
  }

  /**
   * 重置用户密码
   *
   * @param user 用户信息
   * @return 结果
   */
  @Override
  public int resetPwd(SysUser user) {
    return userMapper.updateById(user);
  }

  /**
   * 重置用户密码
   *
   * @param userName 用户名
   * @param password 密码
   * @return 结果
   */
  @Override
  public int resetUserPwd(String userName, String password) {
    return userMapper.resetUserPwd(userName, password);
  }

  /**
   * 新增用户角色信息
   *
   * @param user 用户对象
   */
  public void insertUserRole(SysUser user) {
    this.insertUserRole(user.getUserId(), user.getRoleIds());
  }

  /**
   * 新增用户岗位信息
   *
   * @param user 用户对象
   */
  public void insertUserPost(SysUser user) {
    Long[] posts = user.getPostIds();
    if (StringUtils.isNotEmpty(posts)) {
      // 新增用户与岗位管理
      List<SysUserPost> list = new ArrayList<SysUserPost>(posts.length);
      for (Long postId : posts) {
        SysUserPost up = new SysUserPost();
        up.setUserId(user.getUserId());
        up.setPostId(postId);
        list.add(up);
      }
      userPostMapper.batchUserPost(list);
    }
  }

  /**
   * 新增用户角色信息
   *
   * @param userId 用户ID
   * @param roleIds 角色组
   */
  public void insertUserRole(Long userId, Long[] roleIds) {
    if (StringUtils.isNotEmpty(roleIds)) {
      // 新增用户与角色管理
      List<SysUserRole> list = new ArrayList<SysUserRole>(roleIds.length);
      for (Long roleId : roleIds) {
        SysUserRole ur = new SysUserRole();
        ur.setUserId(userId);
        ur.setRoleId(roleId);
        list.add(ur);
      }
      userRoleMapper.batchUserRole(list);
    }
  }

  @Override
  @Transactional
  public boolean removeById(SysUser user) {
    // 删除用户与角色关联
    userRoleMapper.deleteUserRoleByUserId(user.getUserId());
    // 删除用户与岗位表
    userPostMapper.deleteUserPostByUserId(user.getUserId());
    return super.removeById(user);
  }

  /**
   * 批量删除用户信息
   *
   * @param userIds 需要删除的用户ID
   * @return 结果
   */
  @Override
  @Transactional
  public boolean removeBatchByIds(List<Long> userIds) {
    for (Long userId : userIds) {
      checkUserAllowed(new SysUser(userId));
      checkUserDataScope(userId);
    }
    // 删除用户与角色关联
    userRoleMapper.deleteUserRole(userIds);
    // 删除用户与岗位关联
    userPostMapper.deleteUserPost(userIds);
    return super.removeBatchByIds(userIds);
  }

  /**
   * 导入用户数据
   *
   * @param userList 用户数据列表
   * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
   * @param operName 操作用户
   * @return 结果
   */
  @Override
  public String importUser(List<SysUser> userList, Boolean isUpdateSupport, String operName) {
    if (StringUtils.isNull(userList) || userList.size() == 0) {
      throw new ServiceException("导入用户数据不能为空！");
    }
    int successNum = 0;
    int failureNum = 0;
    StringBuilder successMsg = new StringBuilder();
    StringBuilder failureMsg = new StringBuilder();
    String password = configService.selectConfigByKey("sys.user.initPassword");
    for (SysUser user : userList) {
      try {
        // 验证是否存在这个用户
        SysUser u = userMapper.selectUserByUserName(user.getUserName());
        if (StringUtils.isNull(u)) {
          BeanValidators.validateWithException(validator, user);
          user.setPassword(SecurityUtils.encryptPassword(password));
          user.setCreateBy(operName);
          this.save(user);
          successNum++;
          successMsg.append("<br/>" + successNum + "、账号 " + user.getUserName() + " 导入成功");
        } else if (isUpdateSupport) {
          BeanValidators.validateWithException(validator, user);
          checkUserAllowed(user);
          checkUserDataScope(user.getUserId());
          user.setUpdateBy(operName);
          this.updateUser(user);
          successNum++;
          successMsg.append("<br/>" + successNum + "、账号 " + user.getUserName() + " 更新成功");
        } else {
          failureNum++;
          failureMsg.append("<br/>" + failureNum + "、账号 " + user.getUserName() + " 已存在");
        }
      } catch (Exception e) {
        failureNum++;
        String msg = "<br/>" + failureNum + "、账号 " + user.getUserName() + " 导入失败：";
        failureMsg.append(msg + e.getMessage());
        log.error(msg, e);
      }
    }
    if (failureNum > 0) {
      failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
      throw new ServiceException(failureMsg.toString());
    } else {
      successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
    }
    return successMsg.toString();
  }

  @Override
  public void validateUserList(Collection<Long> ids) {
    if (CollUtil.isEmpty(ids)) {
      return;
    }
    // 获得岗位信息
    List<SysUser> users = userMapper.selectBatchIds(ids);
    Map<Long, SysUser> userMap = CollectionUtil.convertMap(users, SysUser::getUserId);
    // 校验
    ids.forEach(
        id -> {
          SysUser user = userMap.get(id);
          if (user == null) {
            throw new ServiceException("用户不存在");
          }
          if (!SystemStatusEnum.ENABLE.getStatus().equals(user.getStatus())) {
            throw new ServiceException("名字为【" + user.getNickName() + "】的用户已被禁用");
          }
        });
  }

  @Override
  public List<SysUser> getUserList(Set<Long> ids) {
    if (CollUtil.isEmpty(ids)) {
      return Collections.emptyList();
    }
    return userMapper.selectBatchIds(ids);
  }

  @Override
  public List<SysUser> getUserListByDeptIds(Set<Long> deptIds) {
    if (CollUtil.isEmpty(deptIds)) {
      return Collections.emptyList();
    }
    return userMapper.selectListByDeptIds(deptIds);
  }

  @Override
  public List<SysUser> getUsersByPostIds(Set<Long> ids) {
    if (CollUtil.isEmpty(ids)) {
      return Collections.emptyList();
    }
    Set<Long> userIds =
        CollectionUtil.convertSet(userPostMapper.selectListByPostIds(ids), SysUserPost::getUserId);
    if (CollUtil.isEmpty(userIds)) {
      return Collections.emptyList();
    }
    return userMapper.selectBatchIds(userIds);
  }
}
