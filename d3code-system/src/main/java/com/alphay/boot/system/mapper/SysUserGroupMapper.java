package com.alphay.boot.system.mapper;

import com.alphay.boot.system.common.domain.SysUserGroup;
import com.alphay.boot.common.mybatis.mapper.BaseMapperX;
import com.alphay.boot.common.mybatis.query.LambdaQueryWrapperX;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserGroupMapper extends BaseMapperX<SysUserGroup> {

  default List<SysUserGroup> selectList(SysUserGroup userGroup, IPage page) {
    return selectList(
        page,
        new LambdaQueryWrapperX<SysUserGroup>()
            .likeIfPresent(SysUserGroup::getName, userGroup.getName())
            .eqIfPresent(SysUserGroup::getStatus, userGroup.getStatus())
            .betweenIfPresent(
                SysUserGroup::getCreateTime,
                new Object[] {
                  userGroup.getParams().get("beginTime"), userGroup.getParams().get("endTime")
                })
            .orderByDesc(SysUserGroup::getId));
  }

  SysUserGroup selectFullUserGroupInfoById(Long id);
}
