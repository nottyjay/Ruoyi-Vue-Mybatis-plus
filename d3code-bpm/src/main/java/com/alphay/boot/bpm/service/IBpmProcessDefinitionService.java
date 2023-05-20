package com.alphay.boot.bpm.service;

import com.alphay.boot.bpm.model.vo.BpmProcessDefinitionPageItemResponseVo;
import com.alphay.boot.bpm.domain.BpmProcessDefinitionExt;
import com.alphay.boot.bpm.model.dto.BpmProcessDefinitionCreateRequestDTO;
import com.alphay.boot.bpm.model.vo.BpmProcessDefinitionQueryRequestVo;
import com.alphay.boot.common.utils.collection.CollectionUtil;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Bpm 流程定义接口
 *
 * @author Nottyjay
 */
public interface IBpmProcessDefinitionService {

  /**
   * 获得 deploymentIds 对应的 ProcessDefinition 数组
   *
   * @param deploymentIds 部署编号的数组
   * @return 流程定义的数组
   */
  List<ProcessDefinition> getProcessDefinitionListByDeploymentIds(Set<String> deploymentIds);

  /**
   * 获得编号对应的 BpmProcessDefinitionExtDO
   *
   * @param processDefinitionId
   * @return
   */
  BpmProcessDefinitionExt getProcessDefinitionExt(String processDefinitionId);

  /**
   * 获得 id 对应的 Deployment
   *
   * @param id 部署编号
   * @return 流程部署
   */
  Deployment getDeployment(String id);

  /**
   * 获得 ids 对应的 Deployment 数组
   *
   * @param ids 部署编号的数组
   * @return 流程部署的数组
   */
  List<Deployment> getDeployments(Set<String> ids);

  /**
   * 获得 ids 对应的 Deployment Map
   *
   * @param ids 部署编号的数组
   * @return 流程部署 Map
   */
  default Map<String, Deployment> getDeploymentMap(Set<String> ids) {
    return CollectionUtil.convertMap(getDeployments(ids), Deployment::getId);
  }

  /**
   * 获得需要创建的流程定义，是否和当前激活的流程定义相等
   *
   * @param definitionCreateReqDTO
   * @return 是否相等
   */
  boolean isProcessDefinitionEquals(BpmProcessDefinitionCreateRequestDTO definitionCreateReqDTO);

  /**
   * 获得流程定义标识对应的激活的流程定义
   *
   * @param key
   * @return
   */
  ProcessDefinition getActiveProcessDefinition(String key);

  /**
   * 获得对应的ProcessDefinition
   *
   * @param deploymentId
   * @return
   */
  ProcessDefinition getProcessDefinitionByDeploymentId(String deploymentId);

  /**
   * 创建流程定义
   *
   * @param definitionCreateReqDTO
   * @return
   */
  String createProcessDefinition(
      @Valid BpmProcessDefinitionCreateRequestDTO definitionCreateReqDTO);

  /**
   * 更新流程定义状态
   *
   * @param id
   * @param stateCode
   */
  void updateProcessDefinitionState(String id, int stateCode);

  /**
   * 获得对应的 ProcessDefinition
   *
   * @param definitionId
   * @return
   */
  ProcessDefinition getProcessDefinition(String definitionId);

  /**
   * 获得 Bpmn 模型
   *
   * @param processDefinitionId
   * @return
   */
  BpmnModel getBpmnModel(String processDefinitionId);

  /**
   * 获得流程定义列表
   *
   * @param requestVo
   * @return
   */
  List<BpmProcessDefinitionPageItemResponseVo> getProcessDefinitionList(
      BpmProcessDefinitionQueryRequestVo requestVo);

  /**
   * 获得流程定义对应的 BPMN XML
   *
   * @param id
   * @return
   */
  String getProcessDefinitionBpmnXML(String id);

  /**
   * 获得编号对应的 ProcessDefinition
   *
   * @param processDefinitionId
   * @return
   */
  ProcessDefinition getProcessDefinition2(String processDefinitionId);
}
