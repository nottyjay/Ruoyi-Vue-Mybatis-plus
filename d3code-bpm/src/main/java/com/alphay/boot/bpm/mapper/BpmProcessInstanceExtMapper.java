package com.alphay.boot.bpm.mapper;

import com.alphay.boot.bpm.domain.BpmProcessInstanceExt;
import com.alphay.boot.common.mybatis.mapper.BaseMapperX;
import com.alphay.boot.common.mybatis.query.LambdaQueryWrapperX;

import java.util.List;

/**
 * 流程实例 Mapper
 *
 * @author Nottyjay
 */
public interface BpmProcessInstanceExtMapper extends BaseMapperX<BpmProcessInstanceExt> {

  default List<BpmProcessInstanceExt> selectList(BpmProcessInstanceExt processInstanceExt) {
    return selectList(
        new LambdaQueryWrapperX<BpmProcessInstanceExt>()
            .eqIfPresent(BpmProcessInstanceExt::getStartUserId, processInstanceExt.getStartUserId())
            .likeIfPresent(BpmProcessInstanceExt::getName, processInstanceExt.getName())
            .eqIfPresent(
                BpmProcessInstanceExt::getProcessDefinitionId,
                processInstanceExt.getProcessDefinitionId())
            .eqIfPresent(BpmProcessInstanceExt::getCategory, processInstanceExt.getCategory())
            .eqIfPresent(BpmProcessInstanceExt::getStatus, processInstanceExt.getStatus())
            .eqIfPresent(BpmProcessInstanceExt::getResult, processInstanceExt.getResult())
            .betweenIfPresent(
                BpmProcessInstanceExt::getCreateTime,
                new Object[] {
                  processInstanceExt.getParams().get("beginTime"),
                  processInstanceExt.getParams().get("endTime")
                })
            .orderByDesc(BpmProcessInstanceExt::getId));
  }

  default int updateByProcessInstanceId(BpmProcessInstanceExt bpmProcessInstanceExt) {
    return update(
        bpmProcessInstanceExt,
        new LambdaQueryWrapperX<BpmProcessInstanceExt>()
            .eq(
                BpmProcessInstanceExt::getProcessInstanceId,
                bpmProcessInstanceExt.getProcessInstanceId()));
  }

  default BpmProcessInstanceExt selectByProcessInstanceId(String processInstanceId) {
    return selectOne(BpmProcessInstanceExt::getProcessInstanceId, processInstanceId);
  }
}
