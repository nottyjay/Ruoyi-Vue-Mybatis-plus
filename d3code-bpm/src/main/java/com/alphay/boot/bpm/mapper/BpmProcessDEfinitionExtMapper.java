package com.alphay.boot.bpm.mapper;

import com.alphay.boot.bpm.domain.BpmProcessDefinitionExt;
import com.alphay.boot.common.mybatis.mapper.BaseMapperX;

import java.util.Collection;
import java.util.List;

public interface BpmProcessDEfinitionExtMapper extends BaseMapperX<BpmProcessDefinitionExt> {

  default List<BpmProcessDefinitionExt> selectListByProcessDefinitionIds(
      Collection<String> processDefinitionIds) {
    return selectList(BpmProcessDefinitionExt::getProcessDefinitionId, processDefinitionIds);
  }
}
