package com.alphay.boot.bpm.mapper;

import com.alphay.boot.bpm.domain.BpmUserGroup;
import com.alphay.boot.common.mybatis.mapper.BaseMapperX;
import com.alphay.boot.common.mybatis.query.LambdaQueryWrapperX;

import java.util.List;

public interface BpmUserGroupMapper extends BaseMapperX<BpmUserGroup> {

  default List<BpmUserGroup> selectList(BpmUserGroup userGroup) {
    return selectList(
        new LambdaQueryWrapperX<BpmUserGroup>()
            .likeIfPresent(BpmUserGroup::getName, userGroup.getName())
            .eqIfPresent(BpmUserGroup::getStatus, userGroup.getStatus())
            .betweenIfPresent(
                BpmUserGroup::getCreateTime,
                new Object[] {
                  userGroup.getParams().get("beginTime"), userGroup.getParams().get("endTime")
                })
            .orderByDesc(BpmUserGroup::getId));
  }
}
