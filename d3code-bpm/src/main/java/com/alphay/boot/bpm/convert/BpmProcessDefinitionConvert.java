package com.alphay.boot.bpm.convert;

import cn.hutool.core.date.DateUtil;
import com.alphay.boot.bpm.model.vo.BpmProcessDefinitionPageItemResponseVo;
import com.alphay.boot.bpm.domain.BpmForm;
import com.alphay.boot.bpm.domain.BpmProcessDefinitionExt;
import com.alphay.boot.bpm.model.dto.BpmProcessDefinitionCreateRequestDTO;
import com.alphay.boot.bpm.model.vo.BpmProcessDefinitionResponseVo;
import com.alphay.boot.common.utils.collection.CollectionUtil;
import org.flowable.common.engine.impl.db.SuspensionState;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;

/** Bpm 流程定义 Convert */
@Mapper
public interface BpmProcessDefinitionConvert {

  BpmProcessDefinitionConvert INSTANCE = Mappers.getMapper(BpmProcessDefinitionConvert.class);

  BpmProcessDefinitionPageItemResponseVo convert(ProcessDefinition bean);

  BpmProcessDefinitionExt convert2(BpmProcessDefinitionCreateRequestDTO bean);

  default List<BpmProcessDefinitionPageItemResponseVo> convertList(
      List<ProcessDefinition> processDefinitions,
      Map<String, Deployment> deploymentMap,
      Map<String, BpmProcessDefinitionExt> processDefinitionDOMap,
      Map<Long, BpmForm> formMap) {
    return CollectionUtil.convertList(
        processDefinitions,
        definition -> {
          Deployment deployment =
              definition.getDeploymentId() != null
                  ? deploymentMap.get(definition.getDeploymentId())
                  : null;
          BpmProcessDefinitionExt definitionDO = processDefinitionDOMap.get(definition.getId());
          BpmForm form = definitionDO != null ? formMap.get(definitionDO.getFormId()) : null;
          return convert(definition, deployment, definitionDO, form);
        });
  }

  default BpmProcessDefinitionPageItemResponseVo convert(
      ProcessDefinition bean,
      Deployment deployment,
      BpmProcessDefinitionExt processDefinitionExtDO,
      BpmForm form) {
    BpmProcessDefinitionPageItemResponseVo respVO = convert(bean);
    respVO.setSuspensionState(
        bean.isSuspended()
            ? SuspensionState.SUSPENDED.getStateCode()
            : SuspensionState.ACTIVE.getStateCode());
    if (deployment != null) {
      respVO.setDeploymentTime(DateUtil.toLocalDateTime(deployment.getDeploymentTime()));
    }
    if (form != null) {
      respVO.setFormName(form.getName());
    }
    // 复制通用属性
    copyTo(processDefinitionExtDO, respVO);
    return respVO;
  }

  @Mapping(source = "from.id", target = "to.id", ignore = true)
  void copyTo(BpmProcessDefinitionExt from, @MappingTarget BpmProcessDefinitionResponseVo to);
}
