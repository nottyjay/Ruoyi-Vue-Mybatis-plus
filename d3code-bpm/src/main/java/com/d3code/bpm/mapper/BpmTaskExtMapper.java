package com.d3code.bpm.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.d3code.bpm.domain.BpmTaskExt;
import com.d3code.common.mybatis.mapper.BaseMapperX;

import java.util.Collection;
import java.util.List;

public interface BpmTaskExtMapper extends BaseMapperX<BpmTaskExt> {

  default List<BpmTaskExt> selectListByTaskIds(Collection<String> taskIds) {
    return selectList(BpmTaskExt::getTaskId, taskIds);
  }

  default void updateByTaskId(BpmTaskExt entity) {
    update(
        entity, new LambdaQueryWrapper<BpmTaskExt>().eq(BpmTaskExt::getTaskId, entity.getTaskId()));
  }

  default BpmTaskExt selectByTaskId(String taskId) {
    return selectOne(BpmTaskExt::getTaskId, taskId);
  }
}
