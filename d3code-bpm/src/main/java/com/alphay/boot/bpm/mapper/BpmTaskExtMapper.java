package com.alphay.boot.bpm.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.alphay.boot.bpm.domain.BpmTaskExt;
import com.alphay.boot.common.mybatis.mapper.BaseMapperX;

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
