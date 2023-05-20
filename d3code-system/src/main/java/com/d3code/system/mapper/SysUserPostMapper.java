package com.d3code.system.mapper;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.d3code.common.core.domain.entity.SysUser;
import com.d3code.common.mybatis.mapper.BaseMapperX;
import com.d3code.system.domain.SysUserPost;

/**
 * 用户与岗位关联表 数据层
 *
 * @author d3code
 */
public interface SysUserPostMapper extends BaseMapperX<SysUserPost> {
  /**
   * 通过用户ID删除用户和岗位关联
   *
   * @param userId 用户ID
   * @return 结果
   */
  public int deleteUserPostByUserId(Long userId);

  /**
   * 通过岗位ID查询岗位使用数量
   *
   * @param postId 岗位ID
   * @return 结果
   */
  public int countUserPostById(Long postId);

  /**
   * 批量删除用户和岗位关联
   *
   * @param ids 需要删除的数据ID
   * @return 结果
   */
  public int deleteUserPost(Collection<Long> ids);

  /**
   * 批量新增用户岗位信息
   *
   * @param userPostList 用户角色列表
   * @return 结果
   */
  public int batchUserPost(List<SysUserPost> userPostList);

  default List<SysUserPost> selectListByPostIds(Collection<Long> ids) {
    return selectList(SysUserPost::getPostId, ids);
  }
}
