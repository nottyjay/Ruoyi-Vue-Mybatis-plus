package com.alphay.boot.system.api;

import com.alphay.boot.system.service.ISysPostService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * 岗位 api
 *
 * @author Nottyjay
 */
@Component
public class PostApi {

  @Resource private ISysPostService postService;

  /**
   * 校验岗位们是否有效。如下情况，视为无效： 1. 岗位编号不存在 2. 岗位被禁用
   *
   * @param ids
   */
  public void validPostList(Collection<Long> ids) {
    postService.validatePostList(ids);
  }
}
