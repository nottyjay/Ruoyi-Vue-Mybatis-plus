package com.d3code.bpm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.d3code.bpm.domain.BpmUserGroup;
import com.d3code.bpm.model.vo.BpmUserGroupCreateRequestVo;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 用户分组接口
 *
 * @author Nottyjay
 */
public interface IBpmUserGroupService extends IService<BpmUserGroup> {

  /**
   * 获取用户组列表
   *
   * @param userGroup
   * @return
   */
  List<BpmUserGroup> selectUserGroupList(BpmUserGroup userGroup);

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
  List<BpmUserGroup> getUserGroupList(Collection<Long> ids);
}
