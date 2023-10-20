package com.alphay.boot.system.common.service;

import java.util.Collection;
import java.util.List;
import com.alphay.boot.system.common.domain.SysPost;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 岗位信息 服务层
 *
 * @author d3code
 */
public interface ISysPostService extends IService<SysPost> {
  /**
   * 查询岗位信息集合
   *
   * @param post 岗位信息
   * @return 岗位列表
   */
  default List<SysPost> selectPostList(SysPost post) {
    return selectPostList(post, null);
  }

  /**
   * 查询岗位信息集合
   *
   * @param post 岗位信息
   * @param page 分页
   * @return 岗位列表
   */
  List<SysPost> selectPostList(SysPost post, IPage page);

  /**
   * 根据用户ID获取岗位选择框列表
   *
   * @param userId 用户ID
   * @return 选中岗位ID列表
   */
  List<Long> selectPostListByUserId(Long userId);

  /**
   * 校验岗位名称
   *
   * @param post 岗位信息
   * @return 结果
   */
  String checkPostNameUnique(SysPost post);

  /**
   * 校验岗位编码
   *
   * @param post 岗位信息
   * @return 结果
   */
  String checkPostCodeUnique(SysPost post);

  /**
   * 通过岗位ID查询岗位使用数量
   *
   * @param postId 岗位ID
   * @return 结果
   */
  int countUserPostById(Long postId);

  /**
   * 批量删除岗位信息
   *
   * @param postIds 需要删除的岗位ID
   * @return 结果
   */
  boolean deletePostByIds(Long[] postIds);

  /**
   * 校验岗位们是否有效。如下情况，视为无效： 1. 岗位编号不存在 2. 岗位被禁用
   *
   * @param ids 岗位编号数组
   */
  void validatePostList(Collection<Long> ids);
}
