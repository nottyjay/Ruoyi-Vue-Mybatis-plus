package com.d3code.bpm.mapper;

import com.d3code.bpm.domain.BpmUserGroup;
import com.d3code.common.mybatis.mapper.BaseMapperX;
import com.d3code.common.mybatis.query.LambdaQueryWrapperX;

import java.util.Date;
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
