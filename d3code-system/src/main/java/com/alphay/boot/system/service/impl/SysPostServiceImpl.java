package com.alphay.boot.system.service.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import cn.hutool.core.collection.CollUtil;
import com.alphay.boot.common.constant.UserConstants;
import com.alphay.boot.common.exception.ServiceException;
import com.alphay.boot.common.mybatis.service.ServiceImplX;
import com.alphay.boot.common.utils.collection.CollectionUtil;
import com.alphay.boot.common.enums.SystemStatusEnum;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alphay.boot.common.utils.StringUtils;
import com.alphay.boot.system.common.domain.SysPost;
import com.alphay.boot.system.mapper.SysPostMapper;
import com.alphay.boot.system.mapper.SysUserPostMapper;
import com.alphay.boot.system.common.service.ISysPostService;

/**
 * 岗位信息 服务层处理
 *
 * @author d3code
 */
@Service
public class SysPostServiceImpl extends ServiceImplX<SysPostMapper, SysPost>
    implements ISysPostService {

  @Autowired private SysUserPostMapper userPostMapper;

  /**
   * 查询岗位信息集合
   *
   * @param post 岗位信息
   * @return 岗位信息集合
   */
  @Override
  public List<SysPost> selectPostList(SysPost post, IPage page) {
    return this.list(
        page,
        this.lambdaQueryWrapperX()
            .eqIfPresent(SysPost::getStatus, post.getStatus())
            .likeIfPresent(SysPost::getPostCode, post.getPostCode())
            .likeIfPresent(SysPost::getPostName, post.getPostName()));
  }

  /**
   * 根据用户ID获取岗位选择框列表
   *
   * @param userId 用户ID
   * @return 选中岗位ID列表
   */
  @Override
  public List<Long> selectPostListByUserId(Long userId) {
    return this.baseMapper.selectPostListByUserId(userId);
  }

  /**
   * 校验岗位名称是否唯一
   *
   * @param post 岗位信息
   * @return 结果
   */
  @Override
  public String checkPostNameUnique(SysPost post) {
    Long postId = StringUtils.isNull(post.getPostId()) ? -1L : post.getPostId();
    SysPost info = this.baseMapper.checkPostNameUnique(post.getPostName());
    if (StringUtils.isNotNull(info) && info.getPostId().longValue() != postId.longValue()) {
      return UserConstants.NOT_UNIQUE;
    }
    return UserConstants.UNIQUE;
  }

  /**
   * 校验岗位编码是否唯一
   *
   * @param post 岗位信息
   * @return 结果
   */
  @Override
  public String checkPostCodeUnique(SysPost post) {
    Long postId = StringUtils.isNull(post.getPostId()) ? -1L : post.getPostId();
    SysPost info = this.baseMapper.checkPostCodeUnique(post.getPostCode());
    if (StringUtils.isNotNull(info) && info.getPostId().longValue() != postId.longValue()) {
      return UserConstants.NOT_UNIQUE;
    }
    return UserConstants.UNIQUE;
  }

  /**
   * 通过岗位ID查询岗位使用数量
   *
   * @param postId 岗位ID
   * @return 结果
   */
  @Override
  public int countUserPostById(Long postId) {
    return userPostMapper.countUserPostById(postId);
  }

  /**
   * 批量删除岗位信息
   *
   * @param postIds 需要删除的岗位ID
   * @return 结果
   */
  @Override
  public boolean deletePostByIds(Long[] postIds) {
    for (Long postId : postIds) {
      SysPost post = getById(postId);
      if (countUserPostById(postId) > 0) {
        throw new ServiceException(String.format("%1$s已分配,不能删除", post.getPostName()));
      }
    }
    return this.removeByIds(Arrays.asList(postIds));
  }

  @Override
  public void validatePostList(Collection<Long> ids) {
    if (CollUtil.isEmpty(ids)) {
      return;
    }
    // 获得岗位信息
    List<SysPost> posts = this.baseMapper.selectBatchIds(ids);
    Map<Long, SysPost> postMap = CollectionUtil.convertMap(posts, SysPost::getPostId);
    // 校验
    ids.forEach(
        id -> {
          SysPost post = postMap.get(id);
          if (post == null) {
            throw new ServiceException("当前岗位不存在");
          }
          if (!SystemStatusEnum.ENABLE.getStatus().equals(post.getStatus())) {
            throw new ServiceException("岗位(" + post.getPostName() + ") 不处于开启状态，不允许选择");
          }
        });
  }
}
