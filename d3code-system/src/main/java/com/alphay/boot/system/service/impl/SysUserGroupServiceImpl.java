package com.alphay.boot.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.alphay.boot.common.mybatis.query.LambdaQueryWrapperX;
import com.alphay.boot.system.common.domain.SysUserGroup;
import com.alphay.boot.system.common.domain.SysUserGroupRelation;
import com.alphay.boot.system.mapper.SysUserGroupMapper;
import com.alphay.boot.system.common.service.ISysUserGroupService;
import com.alphay.boot.common.enums.SystemStatusEnum;
import com.alphay.boot.common.exception.ServiceException;
import com.alphay.boot.common.utils.collection.CollectionUtil;
import com.alphay.boot.system.mapper.SysUserGroupRelationMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 用户分组接口实现
 *
 * @author Nottyjay
 */
@Service
@Slf4j
@Validated
public class SysUserGroupServiceImpl extends ServiceImpl<SysUserGroupMapper, SysUserGroup>
    implements ISysUserGroupService {

  @Resource private SysUserGroupRelationMapper userGroupRelationMapper;

  @Override
  public void validUserGroups(Collection<Long> ids) {
    if (CollUtil.isEmpty(ids)) {
      return;
    }
    // 获得用户组信息
    List<SysUserGroup> userGroups = baseMapper.selectBatchIds(ids);
    Map<Long, SysUserGroup> userGroupMap =
        CollectionUtil.convertMap(userGroups, SysUserGroup::getId);
    // 校验
    ids.forEach(
        id -> {
          SysUserGroup userGroup = userGroupMap.get(id);
          if (userGroup == null) {
            throw new ServiceException("用户组不存在");
          }
          if (!SystemStatusEnum.ENABLE.getStatus().equals(userGroup.getStatus())) {
            throw new ServiceException("名字为【" + userGroup.getName() + "】的用户组已被禁用");
          }
        });
  }

  @Override
  public List<SysUserGroup> selectUserGroupList(SysUserGroup userGroup, IPage page) {
    return baseMapper.selectList(userGroup, page);
  }

  @Override
  public List<SysUserGroup> getUserGroupList(Collection<Long> ids) {
    return baseMapper.selectBatchIds(ids);
  }

  @Override
  public SysUserGroup getById(Serializable id) {
    return baseMapper.selectFullUserGroupInfoById((Long) id);
  }

  @Override
  public boolean updateUserGroupStatus(SysUserGroup userGroup) {
    return super.updateById(userGroup);
  }

  @Override
  public int updateRelation(SysUserGroup userGroup, List<Long> userIds) {
    return 0;
  }

  @Override
  public boolean save(SysUserGroup entity) {
    boolean result = super.save(entity);
    if (result) {
      batchInsertRelation(entity);
    }
    return result;
  }

  @Override
  public boolean updateById(SysUserGroup entity) {
    boolean result = super.updateById(entity);
    if (result) {
      if (CollUtil.isNotEmpty(entity.getMemberUserIds())) {
        userGroupRelationMapper.delete(
            new LambdaQueryWrapperX<SysUserGroupRelation>()
                .eq(SysUserGroupRelation::getGroupId, entity.getId()));
        batchInsertRelation(entity);
      }
    }
    return result;
  }

  @Override
  public boolean removeById(SysUserGroup entity) {
    boolean result = super.removeById(entity);
    if (result) {
      userGroupRelationMapper.delete(
          new LambdaQueryWrapperX<SysUserGroupRelation>()
              .eq(SysUserGroupRelation::getGroupId, entity.getId()));
    }
    return result;
  }

  private void batchInsertRelation(SysUserGroup userGroup) {
    if (userGroup != null && CollUtil.isNotEmpty(userGroup.getMemberUserIds())) {
      List<SysUserGroupRelation> relations = new ArrayList<>();
      for (Long userId : userGroup.getMemberUserIds()) {
        SysUserGroupRelation relation = new SysUserGroupRelation();
        relation.setGroupId(userGroup.getId());
        relation.setUserId(userId);
        relations.add(relation);
      }
      userGroupRelationMapper.insertBatch(relations);
    }
  }
}
