package com.d3code.bpm.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.d3code.bpm.domain.BpmUserGroup;
import com.d3code.bpm.mapper.BpmUserGroupMapper;
import com.d3code.bpm.model.vo.BpmUserGroupCreateRequestVo;
import com.d3code.bpm.service.IBpmUserGroupService;
import com.d3code.common.enums.SystemStatusEnum;
import com.d3code.common.exception.ServiceException;
import com.d3code.common.utils.collection.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 用户分组接口实现
 *
 * @author Nottyjay
 */
@Service
@Slf4j
@Validated
public class BpmUserGroupServiceImpl extends ServiceImpl<BpmUserGroupMapper, BpmUserGroup>
    implements IBpmUserGroupService {

  @Override
  public void validUserGroups(Collection<Long> ids) {
    if (CollUtil.isEmpty(ids)) {
      return;
    }
    // 获得用户组信息
    List<BpmUserGroup> userGroups = baseMapper.selectBatchIds(ids);
    Map<Long, BpmUserGroup> userGroupMap =
        CollectionUtil.convertMap(userGroups, BpmUserGroup::getId);
    // 校验
    ids.forEach(
        id -> {
          BpmUserGroup userGroup = userGroupMap.get(id);
          if (userGroup == null) {
            throw new ServiceException("用户组不存在");
          }
          if (!SystemStatusEnum.ENABLE.getStatus().equals(userGroup.getStatus())) {
            throw new ServiceException("名字为【" + userGroup.getName() + "】的用户组已被禁用");
          }
        });
  }

  @Override
  public List<BpmUserGroup> selectUserGroupList(BpmUserGroup userGroup) {
    return baseMapper.selectList(userGroup);
  }

  @Override
  public List<BpmUserGroup> getUserGroupList(Collection<Long> ids) {
    return baseMapper.selectBatchIds(ids);
  }
}
