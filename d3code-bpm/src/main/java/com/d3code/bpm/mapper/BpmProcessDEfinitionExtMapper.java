package com.d3code.bpm.mapper;

import com.d3code.bpm.domain.BpmProcessDefinitionExt;
import com.d3code.common.mybatis.mapper.BaseMapperX;

import java.util.Collection;
import java.util.List;

public interface BpmProcessDEfinitionExtMapper extends BaseMapperX<BpmProcessDefinitionExt> {

  default List<BpmProcessDefinitionExt> selectListByProcessDefinitionIds(
      Collection<String> processDefinitionIds) {
    return selectList(BpmProcessDefinitionExt::getProcessDefinitionId, processDefinitionIds);
  }
}
